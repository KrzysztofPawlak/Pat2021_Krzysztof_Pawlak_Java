package com.krzysztof.pawlak.validation;

import com.krzysztof.pawlak.models.Range;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FromToValidator implements ConstraintValidator<ValidFromTo, Range> {

    @Override
    public void initialize(ValidFromTo constraintAnnotation) {
    }

    @Override
    public boolean isValid(Range range, ConstraintValidatorContext context) {
        return range.getFrom() <= range.getTo() || range.getTo() == 0;
    }
}