package com.bjitgroup.emailservice.services;

import com.bjitgroup.emailservice.models.MailContent;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
/**
 * Service class for sending emails using a provided MailService implementation.
 * This class provides methods to send plain text emails and emails with HTML templates.
 * It internally uses a MailService instance to handle the email sending functionality.
 *
 * @author Mallika Dey
 */
@Service
public class MailSender {
    private final MailService mailService;

    /**
     * Constructs a MailSender instance with a MailServiceFactory.
     *
     * @param mailServiceFactory The factory to create a specific MailService implementation.
     */
    public MailSender(MailServiceFactory mailServiceFactory) {
        this.mailService = mailServiceFactory.createMailService();
    }

    /**
     * Sends a plain text email using the configured MailService.
     *
     * @param mailContent The MailContent object containing email details.
     * @throws MessagingException If an error occurs during the email sending process.
     */
    public void sendMail(MailContent mailContent) throws MessagingException {
        MailContentValidation.validateMailContent(mailContent);

        mailService.sendMail(mailContent);
    }

    /**
     * Sends an email with an HTML template using the configured MailService.
     *
     * @param mailContent The MailContent object containing email details.
     * @throws MessagingException If an error occurs during the email sending process.
     */
    public void sendMailWithHTMLTemplate(MailContent mailContent) throws MessagingException {
        MailContentValidation.validateMailContent(mailContent);

        mailService.sendHtmlTemplateMail(mailContent);
    }
}
