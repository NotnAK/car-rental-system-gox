package com.gox.domain.validation.car.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

public class DescriptionNotBlankRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        String d = ctx.getCar().getDescription();
        if (d != null && d.isBlank()) {
            result.addError("description must not be blank");
        }
    }
}