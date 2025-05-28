// PricePerDayNotNullRule.java
package com.gox.domain.validation.car.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

public class PricePerDayNotNullRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        if (ctx.getCar().getPricePerDay() == null) {
            result.addError("pricePerDay is required");
        }
    }
}

