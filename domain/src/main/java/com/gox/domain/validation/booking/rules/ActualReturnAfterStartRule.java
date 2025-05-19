package com.gox.domain.validation.booking.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.BookingValidationContext;

import java.time.OffsetDateTime;

public class ActualReturnAfterStartRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        OffsetDateTime actual = ctx.getActualReturnDate();
        OffsetDateTime start  = ctx.getStart();
        if (actual != null && start != null && actual.isBefore(start)) {
            result.addError(
                    "Actual return date (" + actual + ") cannot be before start date (" + start + ")");
        }
    }
}