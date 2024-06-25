package com.bjitgroup.emailservice.services.impl;

import com.bjitgroup.emailservice.models.SmtpCredential;
import com.bjitgroup.emailservice.services.MailService;
import com.bjitgroup.emailservice.services.MailServiceFactory;

/**
 * Factory for creating SMTP mail service instance
 *
 * @author Mallika Dey
 */
public class SmtpFactory implements MailServiceFactory {
    private SmtpCredential smtpCredential;

    public SmtpFactory(SmtpCredential smtpCredential) {
        this.smtpCredential = smtpCredential;
    }

    /**
     * Creates and returns an instance of SmtpMailService.
     *
     * @return the {@code MailService}
     */
    @Override
    public MailService createMailService() {
        return new SmtpMailService(smtpCredential);
    }
}
