package com.bjitgroup.emailservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Custom validator for checking the total size of attachments.
 * This validator is associated with the {@link ValidAttachmentSize} annotation.
 * It ensures that the total size of attachments does not exceed a specified limit.
 * <p>
 * The limit is set to 25 MB, and the validation succeeds if the total size is within this limit.
 * </p>
 * <p>
 * Note: This validator handles null or empty lists, and in such cases, validation passes.
 * </p>
 *
 * @author Mallika Dey
 */

@Configuration
public class AttachmentSizeValidator implements ConstraintValidator<ValidAttachmentSize, ArrayList<File>> {
    @Override
    public void initialize(ValidAttachmentSize constraintAnnotation) {
    }

    @Override
    public boolean isValid(ArrayList<File> files, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(files) || files.isEmpty()) {
            return true;
        }
        long totalSize = 0;
        for (File attachment : files) {
            totalSize += attachment.length();
        }

        return totalSize <= 25 * 1024 * 1024;
    }
}
