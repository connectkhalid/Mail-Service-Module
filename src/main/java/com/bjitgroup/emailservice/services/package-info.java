package com.bjitgroup.emailservice.services;
/**
 * Provides services for sending emails.
 * This package contains implementations for email sending functionality,
 * including integration with AWS SES and related utilities.
 *
 * <p>
 * Classes in this package include:
 * <ul>
 *     <li>{@link com.bjitgroup.emailservice.services.MailSender}: Service class for sending emails using a provided MailService implementation.</li>
 *     <li>{@link com.bjitgroup.emailservice.services.MailService}: Interface defining the contract for email sending services.</li>
 *     <li>{@link com.bjitgroup.emailservice.services.MailServiceFactory}: Factory interface for creating specific MailService implementations.</li>
 *     <li>{@link com.bjitgroup.emailservice.services.MailContentValidation}: Utility class for validating {@link com.bjitgroup.emailservice.models.MailContent} objects using Java's Bean Validation API.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Classes in this package may interact with the following packages:
 * <ul>
 *     <li>{@link com.bjit.mailservice.models}: Contains classes representing email-related data models.</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 */