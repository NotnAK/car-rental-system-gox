package com.gox.domain.validation.car.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

import java.time.Year;

public class YearRangeRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        Integer y = ctx.getCar().getYear();
        int thisYear = Year.now().getValue();
        if (y < 2010 || y > thisYear) {
            result.addError("year must be between 2010 and " + thisYear);
        }
    }
}
