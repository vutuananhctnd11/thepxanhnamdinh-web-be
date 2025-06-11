package com.graduate.be_txnd_fanzone.validator.Length;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class LengthValidator implements ConstraintValidator<LengthConstraint, String> {
    private int min;
    private int max;

    @Override
    public void initialize(LengthConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!Objects.isNull(value)) {
            int length = value.length();
            return length >= min && length <= max;
        }
        return true;
    }
}
