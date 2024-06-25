package com.bjitgroup.emailservice.services;

import com.bjitgroup.emailservice.exception.EmailException;
import com.bjitgroup.emailservice.models.MailContent;
import com.bjitgroup.emailservice.models.SmtpCredential;
import com.bjitgroup.emailservice.services.impl.SmtpMailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Mallika Dey
 */
@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
public class SMTPMailServiceTest {
    @Mock
    private SmtpCredential smtpCredential;
    @InjectMocks
    private SmtpMailService smtpMailService;
    private MailContent mailContent;
    @Value("${file.valid.location}")
    private String validFile;
    @Value("${file.invalid.location}")
    private String invalidFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mailContent = mock(MailContent.class);
        mailContentFieldMock();
        smtpCredentialMock();
    }

    @Test
    public void testSendMail() throws MessagingException {
        when(mailContent.getBcc()).thenReturn(new ArrayList<>(List.of("abc2@gmail.com")));
        when(mailContent.getAttachments())
                .thenReturn(new ArrayList<>(
                        List.of(new File(validFile))));

        String result = smtpMailService.sendMail(mailContent);

        assertEquals("mail sent successfully", result);
    }

    @Test
    public void testSendHtmlTemplateMailUsingCustomizeTemplate_Successful() throws MessagingException {
        when(mailContent.getBcc()).thenReturn(null);
        when(mailContent.getAttachments()).thenReturn(new ArrayList<>(
                List.of(new File(validFile))));
        when(mailContent.getHtmlTemplate()).thenReturn(new File(validFile));

        String result = smtpMailService.sendHtmlTemplateMail(mailContent);

        assertEquals("HTML template mail has been sent successfully.", result);
    }

    @Test
    public void testSendHtmlTemplateMailUsingCustomizeTemplate_Failed() throws MessagingException {
        when(mailContent.getBcc()).thenReturn(null);
        when(mailContent.getAttachments()).thenReturn(new ArrayList<>(
                List.of(new File(validFile))));
        when(mailContent.getHtmlTemplate()).thenReturn(new File(invalidFile));

        assertThrows(IllegalArgumentException.class, () -> smtpMailService.sendHtmlTemplateMail(mailContent));
    }

    @Test
    public void testSendHtmlTemplateMailUsingDefaultTemplate_Successful() throws MessagingException {
        String result = smtpMailService.sendHtmlTemplateMail(mailContent);

        assertEquals("HTML template mail has been sent successfully.", result);
    }

    @Test
    public void testSendMailFail() throws MessagingException {
        when(mailContent.getAttachments())
                .thenReturn(new ArrayList<>(List.of(new File(invalidFile))));

        assertThrows(EmailException.class, () ->
                smtpMailService.sendMail(mailContent));
    }

    private void smtpCredentialMock() {
        when(smtpCredential.getSmtpHost()).thenReturn("smtp.gmail.com");
        when(smtpCredential.getSmtpPort()).thenReturn("587");
        when(smtpCredential.getUserMail()).thenReturn("username");
        when(smtpCredential.getUserPassword()).thenReturn("pass");
        when(smtpCredential.getEnableStartTls()).thenReturn(true);
        when(smtpCredential.getSmtpAuth()).thenReturn(true);
    }

    private void mailContentFieldMock() {
        when(mailContent.getFrom()).thenReturn("khalid.hasan.bjit@gmail.com");
        when(mailContent.getTo()).thenReturn(new ArrayList<>(List.of("mallika.dey@bjitgroup.com")));
        when(mailContent.getCc()).thenReturn(new ArrayList<>(List.of("abc1@gmail.com")));
        when(mailContent.getSubject()).thenReturn("Test Subject");
        when(mailContent.getBody()).thenReturn("Test Body");
    }
}
