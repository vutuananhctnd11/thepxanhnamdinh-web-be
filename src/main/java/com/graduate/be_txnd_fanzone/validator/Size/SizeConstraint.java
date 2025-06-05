package com.graduate.be_txnd_fanzone.validator.Size;

import com.graduate.be_txnd_fanzone.validator.Email.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {SizeValidator.class})
public @interface SizeConstraint {
    String message() default "INVALID_SIZE";

    String name();

    int min();

    int max();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
