package com.graduate.be_txnd_fanzone.validator.Length;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {LengthValidator.class})
public @interface LengthConstraint {
    String message() default "INVALID_LENGTH";

    String name();

    int min();

    int max();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
