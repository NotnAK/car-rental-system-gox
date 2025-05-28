package com.gox.domain.validation.car.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

public class SeatsRangeRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        Integer s = ctx.getCar().getSeats();
        if (s < 1 || s > 9) {
            result.addError("seats must be between 1 and 9");
        }
    }
}