package com.gox.domain.validation.booking.rules;

import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.booking.BookingValidationContext;

public class CarLocationIdNotNullRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult vr) {
        if (ctx.getCarLocationId() == null) {
            vr.addError("Car has no assigned location");
        }
    }
}