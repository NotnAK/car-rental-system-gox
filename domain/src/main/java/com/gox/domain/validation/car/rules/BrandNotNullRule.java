// BrandNotNullRule.java
package com.gox.domain.validation.car.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

public class BrandNotNullRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        if (ctx.getCar().getBrand() == null) {
            result.addError("brand must not be null");
        }
    }
}
