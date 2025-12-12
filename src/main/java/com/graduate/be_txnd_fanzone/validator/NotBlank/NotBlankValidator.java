package com.graduate.be_txnd_fanzone.validator.NotBlank;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator<NotBlankConstraint, Object> {

    @Override
    public void initialize(NotBlankConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String val) {
            return !val.trim().isEmpty();
        } else {
            return value != null;
        }

    }
}
