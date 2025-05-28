package com.gox.domain.validation.booking.rules;


import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.BookingValidationContext;

public class ActualReturnNotNullRule implements ValidationRule<BookingValidationContext> {
    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        if (BookingStatus.APPROVED.equals(ctx.getStatus())
                && ctx.getActualReturnDate() == null) {
            result.addError("Actual return date/time must not be null when approving a booking");
        }
    }
}