package com.gox.domain.validation.booking.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.BookingValidationContext;

public class DatesNotNullRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        if (ctx.getStart() == null || ctx.getEnd() == null) {
            result.addError("Start and end dates are required");
        }
    }
}
