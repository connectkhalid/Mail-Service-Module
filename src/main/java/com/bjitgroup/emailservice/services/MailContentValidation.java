package com.bjitgroup.emailservice.services;

import com.bjitgroup.emailservice.constants.MessageConstant;
import com.bjitgroup.emailservice.models.MailContent;
import jakarta.validation.*;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * Utility class for validating {@link MailContent} objects using Java's Bean Validation API.
 *
 * @author Mallika Dey
 */
public class MailContentValidation {
    /**
     * Validates the provided {@link MailContent} object.
     *
     * @param mailContent The {@link MailContent} object to be validated.
     * @throws IllegalArgumentException       If {@code mailContent} is {@code null}.
     * @throws ConstraintViolationException If validation fails, containing information about the violations.
     */
    public static void validateMailContent(MailContent mailContent) {
        if (ObjectUtils.isEmpty(mailContent)) {
            throw new IllegalArgumentException(MessageConstant.not_null_MailContent);
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MailContent>> violations = validator.validate(mailContent);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
