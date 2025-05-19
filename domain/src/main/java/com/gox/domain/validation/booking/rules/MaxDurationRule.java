package com.gox.domain.validation.booking.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.BookingValidationContext;

import java.time.OffsetDateTime;

public class MaxDurationRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        OffsetDateTime start = ctx.getStart();
        OffsetDateTime end   = ctx.getEnd();
        if (start != null && end != null) {
            // если фактический end > start + 1 месяц
            OffsetDateTime maxAllowedEnd = start.plusMonths(1);
            if (end.isAfter(maxAllowedEnd)) {
                result.addError(
                        "Booking duration cannot exceed one month"
                );
            }
        }
    }
}