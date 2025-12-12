package com.graduate.be_txnd_fanzone.validator.Length;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class LengthValidator implements ConstraintValidator<LengthConstraint, Object> {
    private int min;
    private int max;

    @Override
    public void initialize(LengthConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (!Objects.isNull(value)) {
            int length = 0;
            if (value instanceof String str) {
                length = str.length();
            } else if (value instanceof Number num) {
                length = num.toString().length();
            }
            return length >= min && length <= max;
        }
        return true;
    }
}
