package com.graduate.be_txnd_fanzone.validator.Email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.regex.Pattern;


public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!Objects.isNull(value)) {
            return EMAIL_REGEX.matcher(value).matches();
        } else {
            return true;
        }

    }
}
