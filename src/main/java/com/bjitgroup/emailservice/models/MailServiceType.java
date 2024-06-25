package com.bjitgroup.emailservice.models;

/**
 * Represents the type of mail services
 *
 * @author Mallika Dey
 */
public enum MailServiceType {
    /**
     * Represents the SendGrid mail service.
     */
    SENDGRID("SENDGRID"),
    /**
     * Represents the Amazon Web Services mail service.
     */
    AWS("AWS"),
    /**
     * Represents the Simple Mail Transfer Protocol (SMTP) mail service.
     */
    SMTP("SMTP");
    private final String value;

    MailServiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
