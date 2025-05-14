package com.gox.domain.validation.booking.rules;


import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.BookingValidationContext;

public class DateOrderRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        if (ctx.getStart() == null || ctx.getEnd() == null) {
            result.addError("Start and end dates are required");
        } else if (!ctx.getStart().isBefore(ctx.getEnd())) {
            result.addError("Start date must be before end date");
        }
    }
}