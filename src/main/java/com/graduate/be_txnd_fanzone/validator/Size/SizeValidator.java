package com.graduate.be_txnd_fanzone.validator.Size;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class SizeValidator implements ConstraintValidator<SizeConstraint, Object> {
    private int min;
    private int max;

    @Override
    public void initialize(SizeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }
        if (value instanceof Number number) {
            return number.intValue() >= min && number.intValue() <= max;
        } else {
            return false;
        }
    }
}
