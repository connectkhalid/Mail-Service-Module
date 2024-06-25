package com.bjitgroup.emailservice.services.impl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.bjitgroup.emailservice.constants.MessageConstant;
import com.bjitgroup.emailservice.models.MailContent;
import com.bjitgroup.emailservice.services.LoadMailTemplate;
import com.bjitgroup.emailservice.services.MailService;
import com.bjitgroup.emailservice.validators.MailValidation;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Implementation of the MailService interface for sending emails using AWS SES.
 * This class handles the integration with AWS SES and provides the logic for sending email messages.
 *
 * @author Mallika Dey
 */
public class AwsMailService implements MailService, MailValidation, LoadMailTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(AwsMailService.class);
    private final AmazonSimpleEmailService client;

    /**
     * Constructs an AwsMailService object with the provided Amazon Simple Email Service client.
     *
     * @param client the Amazon SES client used for sending emails
     */
    public AwsMailService(AmazonSimpleEmailService client) {
        this.client = client;
    }

    /**
     * Sends an email using the Amazon Simple Email Service with the provided MailContent.
     *
     * @param mailContent The content of the email to be sent.
     * @return A message indicating the result of the email sending operation.
     * It could be a success message or an error message.
     * @throws MessagingException If an error occurs during the email sending process.
     */
    @Override
    public String sendMail(MailContent mailContent) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());

        MimeMessage message = generateMimeMessage(mailContent, session);

        MimeMultipart msg_body = new MimeMultipart("alternative");

        return mailSend(null, mailContent, message);
    }

    /**
     * Sends an HTML template-based email using the AWS SES with the provided MailContent.
     * If a specific HTML template is not provided in the MailContent, the default "welcome.html" template is loaded.
     * The dynamic content specified in the MailContent is replaced in the template before sending the email.
     *
     * @param mailContent The MailContent object containing information about the email to be sent,
     *                    including the HTML template and dynamic content.
     * @return A unique message identifier for tracking the sent email.
     * @throws MessagingException If an error occurs during the email sending process.
     */
    @Override
    public String sendHtmlTemplateMail(MailContent mailContent) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());

        MimeMessage message = generateMimeMessage(mailContent, session);
        File htmlTemplate = mailContent.getHtmlTemplate();
        if (!ObjectUtils.isEmpty(htmlTemplate)) {
            validateHtmlTemplate(htmlTemplate);
        }

        String htmlContent = loadHtmlTemplate(mailContent.getHtmlTemplate(), mailContent.getObjectMap());
        mailSend(htmlContent, mailContent, message);
        return MessageConstant.sendMail_success;
    }

    private MimeMessage generateMimeMessage(MailContent mailContent,
                                            Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);

        message.setSubject(mailContent.getSubject(), "UTF-8");

        if (!ObjectUtils.isEmpty(mailContent.getTo())) {
            setRecipients(message, Message.RecipientType.TO, mailContent.getTo());
        }
        if (!ObjectUtils.isEmpty(mailContent.getCc())) {
            setRecipients(message, Message.RecipientType.CC, mailContent.getCc());
        }
        if (!ObjectUtils.isEmpty(mailContent.getBcc())) {
            setRecipients(message, Message.RecipientType.BCC, mailContent.getBcc());
        }
        message.setFrom(new InternetAddress(mailContent.getFrom()));
        return message;
    }

    private void setRecipients(MimeMessage message, Message.RecipientType type,
                               ArrayList<String> addresses) throws MessagingException {
        ArrayList<InternetAddress> addressTo = new ArrayList<InternetAddress>();
        for (String address : addresses) {
            addressTo.add(new InternetAddress(address));
        }
        message.setRecipients(type, addressTo.toArray(new InternetAddress[0]));
    }

    private String mailSend(String htmlContent, MailContent mailContent
            , MimeMessage message) throws MessagingException {
        try {
            LOGGER.info("Attempting to send an template email through Amazon SES");
            MimeBodyPart htmlPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();

            if (!ObjectUtils.isEmpty(mailContent.getBody()))
                textPart.setContent(mailContent.getBody(), "text/plain; charset=UTF-8");
            else
                textPart.setContent(" ", "text/plain; charset=UTF-8");

            if (!ObjectUtils.isEmpty(htmlContent))
                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

            else
                htmlPart.setContent("", "text/html; charset=UTF-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(textPart);

            if (!ObjectUtils.isEmpty(mailContent.getAttachments())) {
                for (File file : mailContent.getAttachments()) {
                    MimeBodyPart filePart = new MimeBodyPart();
                    checkFileCompatibility(file);
                    filePart.setDataHandler(new DataHandler(new FileDataSource(file)));
                    filePart.setFileName(file.getName());
                    multipart.addBodyPart(filePart);
                }
            }
            message.setContent(multipart);

            PrintStream out = System.out;
            message.writeTo(out);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);

            client.sendRawEmail(new SendRawEmailRequest(
                    new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()))));
        } catch (IOException ex) {
            LOGGER.error(MessageConstant.process_attachment_error, ex);
            return MessageConstant.sendMail_error;
        }
        return MessageConstant.sendMail_success;
    }
}