package com.gox.domain.validation.car.rules;

import com.gox.domain.entity.car.Car;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

public class CarBrandNotEmptyRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        Car c = ctx.getCar();
        if (c != null && (c.getBrand() == null || c.getBrand().isBlank())) {
            result.addError("Car brand must not be blank");
        }
    }
}