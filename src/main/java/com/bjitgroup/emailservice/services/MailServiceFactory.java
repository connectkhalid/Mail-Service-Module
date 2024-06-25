package com.bjitgroup.emailservice.services;

/**
 * Factory interface for creating instances of MailService.
 * Implementations of this interface should provide a way to create a specific MailService.
 *
 * @author Mallika Dey
 */
public interface MailServiceFactory {
    /**
     * Creates and returns an instance of MailService.
     *
     * @return An MailService instance.
     */
    MailService createMailService();
}
