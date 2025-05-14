package com.gox.domain.validation.car.rules;


import com.gox.domain.entity.car.Car;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;

public class CarNotNullRule implements ValidationRule<CarValidationContext> {
    @Override
    public void validate(CarValidationContext ctx, ValidationResult result) {
        if (ctx.getCar() == null) {
            result.addError("Car object must not be null");
        }
    }
}