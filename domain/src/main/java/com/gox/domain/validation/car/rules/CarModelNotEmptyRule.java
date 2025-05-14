package com.gox.domain.validation.car.rules;

import com.gox.domain.entity.car.Car;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

public class CarModelNotEmptyRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        Car c = ctx.getCar();
        if (c != null && (c.getModel() == null || c.getModel().isBlank())) {
            result.addError("Car model must not be blank");
        }
    }
}