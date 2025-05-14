package com.gox.domain.validation.booking.rules;

import com.gox.domain.validation.booking.BookingValidationContext;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;

public class CarIdNotNullRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        if (ctx.getCarId() == null) {
            result.addError("Car ID is required");
        }
    }
}