package com.gox.domain.validation.booking.rules;

import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.BookingValidationContext;

public class MustBeApprovedRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        if (ctx.getStatus() != BookingStatus.APPROVED) {
            result.addError(
                    "Only APPROVED bookings can be completed, current is " + ctx.getStatus());
        }
    }
}
