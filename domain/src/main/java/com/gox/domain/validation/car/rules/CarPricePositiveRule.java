package com.gox.domain.validation.car.rules;

import com.gox.domain.entity.car.Car;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

import java.math.BigDecimal;

public class CarPricePositiveRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        Car c = ctx.getCar();
        if (c != null) {
            BigDecimal p = c.getPricePerDay();
            if (p == null || p.compareTo(BigDecimal.ZERO) <= 0) {
                result.addError("Price per day must be positive");
            }
        }
    }
}