package com.bjitgroup.emailservice.validators;

import com.bjitgroup.emailservice.constants.MessageConstant;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Interface for validating files based on compatibility criteria.
 * Implementations should provide methods for various file validation checks.
 *
 * @author Mallika Dey
 */
public interface MailValidation {

    /**
     * Checks the compatibility of a file based on its existence and file type.
     *
     * @param file The file to be checked for compatibility.
     * @throws IllegalArgumentException if the file is null or if it has an unsupported file type.
     */
    default void checkFileCompatibility(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException(MessageConstant.fileNotExists + file.getAbsolutePath());
        }
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        List<String> blockedFileTypes = Arrays.asList(
                "ade", "adp", "apk", "appx", "appxbundle", "bat", "cab", "chm", "cmd", "com", "cpl",
                "diagcab", "diagcfg", "diagpack", "dll", "dmg", "ex", "ex_", "exe", "hta", "img", "ins",
                "iso", "isp", "jar", "jnlp", "js", "jse", "lib", "lnk", "mde", "mjs", "msc", "msi",
                "msix", "msixbundle", "msp", "mst", "nsh", "pif", "ps1", "scr", "sct", "shb", "sys",
                "vb", "vbe", "vbs", "vhd", "vxd", "wsc", "wsf", "wsh", "xll");
        if (blockedFileTypes.contains(fileExtension)) {
            throw new RuntimeException(MessageConstant.unsupported_file_type + fileExtension + " - " + fileName);
        }
    }
    default boolean validateHtmlTemplate(File htmlTemplate){
        if (htmlTemplate != null && htmlTemplate.isFile() && htmlTemplate.getName().endsWith(".html")) {
            // Check file size
            long fileSizeInBytes = htmlTemplate.length();
            long fileSizeInMB = fileSizeInBytes / (1024 * 1024); // Convert bytes to MB
            if (fileSizeInMB <= 5) {
                return true;
            } else {
                throw new IllegalArgumentException(MessageConstant.maximum_attachment_file_size);
            }
        } else {
            throw new IllegalArgumentException(MessageConstant.html_file_type_mismatched);
        }
    }
}
