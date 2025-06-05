package com.graduate.be_txnd_fanzone.validator.Size;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class SizeValidator implements ConstraintValidator<SizeConstraint, String> {
    private int min;
    private int max;

    @Override
    public void initialize(SizeConstraint constraintAnnotation) {
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
