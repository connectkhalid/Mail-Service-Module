package com.bjitgroup.emailservice.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailResult;
import com.bjitgroup.emailservice.models.MailContent;
import com.bjitgroup.emailservice.services.impl.AwsMailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * @author Mallika Dey
 */
@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
public class AwsMailServiceTest {
    @Mock
    private AmazonSimpleEmailService amazonSimpleEmailService;
    @InjectMocks
    private AwsMailService awsMailService;
    private MailContent mailContent;

    @Value("${file.valid.location}")
    private String validFile;
    @Value("${file.invalid.location}")
    private String invalidFile;

    @BeforeEach
    void setUp() {
        mailContent = createMailContent();
    }

    @Test
    public void sendMailUsingAWSSESShouldExecuteSuccessfully() throws MessagingException {
        mailContent.setAttachments(new ArrayList<>(
                List.of(new File(validFile))));

        when(amazonSimpleEmailService.sendRawEmail(any(SendRawEmailRequest.class)))
                .thenReturn(new SendRawEmailResult());

        String msg = awsMailService.sendMail(mailContent);
        assertThat(msg).isEqualTo("mail sent successfully");
    }

    @Test
    public void sendMailUsingAWSSES_FileNotFound_ShouldFail() throws MessagingException {
        mailContent.setAttachments(new ArrayList<>(List.of(new File("abc.txt"))));

        when(amazonSimpleEmailService.sendRawEmail(any(SendRawEmailRequest.class)))
                .thenReturn(new SendRawEmailResult());

        assertThat(awsMailService.sendMail(mailContent)).isEqualTo("mail sending failed");
    }

    @Test
    public void sendHTMLTemplateMailUsingAWSSES_ShouldExecuteSuccessfully() throws MessagingException {
        when(amazonSimpleEmailService.sendRawEmail(any(SendRawEmailRequest.class)))
                .thenReturn(new SendRawEmailResult());

        assertThat(awsMailService.sendHtmlTemplateMail(mailContent)).isEqualTo("mail sent successfully");
    }

    @Test
    public void testAwsSendHtmlTemplateMailUsingCustomizeTemplate_Failed() throws MessagingException {
        when(amazonSimpleEmailService.sendRawEmail(any(SendRawEmailRequest.class)))
                .thenReturn(new SendRawEmailResult());
        mailContent.setHtmlTemplate(new File(invalidFile));

        assertThrows(IllegalArgumentException.class, () -> awsMailService.sendHtmlTemplateMail(mailContent));
    }

    @Test
    public void testAwsSendHtmlTemplateMailUsingCustomizeTemplate_Successful() throws MessagingException {
        when(amazonSimpleEmailService.sendRawEmail(any(SendRawEmailRequest.class)))
                .thenReturn(new SendRawEmailResult());
        mailContent.setHtmlTemplate(new File(validFile));

        assertThat(awsMailService.sendHtmlTemplateMail(mailContent))
                .isEqualTo("mail sent successfully");
    }

    private MailContent createMailContent() {
        MailContent mailContent = new MailContent();
        mailContent.setFrom("abcd@gmail.com");
        mailContent.setTo(new ArrayList<>(Arrays.asList("abcd1@gmail.com")));
        mailContent.setCc(new ArrayList<>(Arrays.asList("abc@gmail.com")));
        mailContent.setBcc(new ArrayList<>(Arrays.asList("abc1@gmail.com")));
        mailContent.setSubject("Testing aws ses mail service");
        mailContent.setBody("testing aws ses using mockito");

        return mailContent;
    }

}
