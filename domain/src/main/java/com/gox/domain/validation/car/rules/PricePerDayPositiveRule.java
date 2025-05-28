package com.gox.domain.validation.car.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

import java.math.BigDecimal;

public class PricePerDayPositiveRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        BigDecimal p = ctx.getCar().getPricePerDay();
        if (p != null && p.compareTo(BigDecimal.ZERO) <= 0) {
            result.addError("pricePerDay must be positive");
        }
    }
}
