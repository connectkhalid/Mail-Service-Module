package com.bjitgroup.emailservice.services.impl;

import com.bjitgroup.emailservice.services.MailService;
import com.bjitgroup.emailservice.services.MailServiceFactory;
import com.sendgrid.SendGrid;

/**
 * Factory for creating SendGrid mail service instance
 *
 * @author Mallika Dey
 */
public class SendGridFactory implements MailServiceFactory {
    private String sendGridApiKey;

    public SendGridFactory(String sendGridApiKey) {
        this.sendGridApiKey = sendGridApiKey;
    }

    @Override
    public MailService createMailService() {
        return new SendGridMailService(sendGrid());
    }

    public SendGrid sendGrid() {
        return new SendGrid(sendGridApiKey);
    }
}
