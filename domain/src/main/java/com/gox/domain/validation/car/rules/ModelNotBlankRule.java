package com.gox.domain.validation.car.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

public class ModelNotBlankRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        String m = ctx.getCar().getModel();
        if (m != null && m.isBlank()) {
            result.addError("model must not be blank");
        }
    }
}