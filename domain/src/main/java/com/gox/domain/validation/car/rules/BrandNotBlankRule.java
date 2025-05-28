package com.gox.domain.validation.car.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

public class BrandNotBlankRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        String b = ctx.getCar().getBrand();
        if (b != null && b.isBlank()) {
            result.addError("brand must not be blank");
        }
    }
}