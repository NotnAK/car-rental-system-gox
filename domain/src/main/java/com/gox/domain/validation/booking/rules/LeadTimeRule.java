package com.gox.domain.validation.booking.rules;

import com.gox.domain.validation.booking.BookingValidationContext;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;

import java.time.OffsetDateTime;

public class LeadTimeRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        if (ctx.getStart() != null) {
            OffsetDateTime earliest = OffsetDateTime.now().plusHours(4);
            if (ctx.getStart().isBefore(earliest)) {
                result.addError(
                        "Booking must start at least 4 hours from now (earliest: " + earliest + ")"
                );
            }
        }
    }
}