package com.bjitgroup.emailservice.validators;

import com.bjitgroup.emailservice.constants.MessageConstant;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;

import java.lang.annotation.*;

/**
 * Custom validation annotation for checking the total size of attachments.
 * The total size must not exceed 25 MB.
 * This annotation can be applied to a field representing a list of attachments.
 *
 * @author Mallika Dey
 */
@Documented
@Constraint(validatedBy = { AttachmentSizeValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Size(max = 25, groups = { Default.class })
@ReportAsSingleViolation
public @interface ValidAttachmentSize {
    String message() default MessageConstant.valid_Attachment_Size_message;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
