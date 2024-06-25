package com.bjitgroup.emailservice.models;

import com.bjitgroup.emailservice.constants.MessageConstant;
import com.bjitgroup.emailservice.validators.ValidAttachmentSize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the content of an email message.
 *
 * @author Mallika Dey
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailContent {
    @Email(message = MessageConstant.from_email_address_invalid)
    private String from;

    @NotEmpty(message = MessageConstant.to_email_address_empty)
    private ArrayList<@NotNull String> to;
    private ArrayList<@NotNull String> cc;
    private ArrayList<@NotNull String> bcc;
    private String subject;
    private String body;
    private File htmlTemplate;
    private HashMap<String, Object> objectMap;

    @ValidAttachmentSize(groups = {Default.class})
    private ArrayList<File> attachments;
}
