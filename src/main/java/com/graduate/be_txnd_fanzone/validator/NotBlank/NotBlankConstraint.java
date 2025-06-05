package com.graduate.be_txnd_fanzone.validator.NotBlank;

import com.graduate.be_txnd_fanzone.validator.DateOfBirth.DobValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {NotBlankValidator.class})
public @interface NotBlankConstraint {
    String message() default "FIELD_NOT_BLANK";

    String name();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
