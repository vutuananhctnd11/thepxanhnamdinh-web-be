package com.graduate.be_txnd_fanzone.validator.Phone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<PhoneConstraint, String> {
    private static final Pattern REGEX_PHONE_NUMBER = Pattern.compile("^(0|\\+84)\\d{9}$");

    @Override
    public void initialize(PhoneConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }
        return REGEX_PHONE_NUMBER.matcher(value).matches();
    }
}
