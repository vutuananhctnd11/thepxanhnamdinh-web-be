package com.graduate.be_txnd_fanzone.validator.DateOfBirth;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "INVALID_DOB";

    int min();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
