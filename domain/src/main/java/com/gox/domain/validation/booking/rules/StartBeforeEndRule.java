package com.gox.domain.validation.booking.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.BookingValidationContext;

public class StartBeforeEndRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        if (ctx.getStart() != null && ctx.getEnd() != null
                && !ctx.getStart().isBefore(ctx.getEnd())) {
            result.addError("Start date must be before end date");
        }
    }
}
