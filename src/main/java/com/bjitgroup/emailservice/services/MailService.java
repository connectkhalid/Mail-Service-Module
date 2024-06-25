package com.bjitgroup.emailservice.services;

import com.bjitgroup.emailservice.models.MailContent;
import jakarta.mail.MessagingException;

/**
 * Interface for sending email messages.
 * Implementations of this interface handle the actual email sending logic.
 * <p>
 * Implementing classes should provide a concrete implementation of the sendMail method.
 * </p>
 *
 * @author Mallika Dey
 */
public interface MailService {
    /**
     * Sends a simple text email
     *
     * @param mailContent The MailContent object containing information about the email to be sent,
     *                    including the HTML template and dynamic content.
     * @return String for mail sending confirmation
     * @throws MessagingException If an error occurs during the email sending process.
     */
    String sendMail(MailContent mailContent) throws MessagingException;

    /**
     * Sends an HTML template-based email
     *
     * @param mailContent The MailContent object containing information about the email to be sent,
     *                    including the HTML template and dynamic content.
     * @return A unique message identifier for tracking the sent email.
     * @throws MessagingException If an error occurs during the email sending process.
     */
    String sendHtmlTemplateMail(MailContent mailContent) throws MessagingException;
}

