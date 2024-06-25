package com.bjitgroup.emailservice.models;

import lombok.*;

/**
 * Represents SMTP credentials required for sending emails.
 *
 * @author Mallika Dey
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SmtpCredential {
    private Boolean enableStartTls;
    private Boolean smtpAuth;
    private String smtpPort;
    private String smtpHost;
    private String userMail;
    private String userPassword;
}
