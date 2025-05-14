package com.gox.domain.validation.booking.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.BookingValidationContext;

public class PickupLocationIdNotNullRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        if (ctx.getPickupLocationId() == null) {
            result.addError( "Pickup location ID is required");
        }
    }
}