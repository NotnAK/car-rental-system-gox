package com.gox.domain.validation.booking.rules;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.repository.BookingRepository;
import com.gox.domain.validation.booking.BookingValidationContext;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;

import java.time.OffsetDateTime;

public class GapRule implements ValidationRule<BookingValidationContext> {
    private final BookingRepository bookingRepository;

    public GapRule(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void validate(BookingValidationContext ctx, ValidationResult result) {
        if (ctx.getStart() != null && ctx.getEnd() != null) {
            OffsetDateTime endPlus   = ctx.getEnd().plusDays(1);
            OffsetDateTime startMinus = ctx.getStart().minusDays(1);
            boolean conflict = bookingRepository.existsConflict(
                    ctx.getCarId(),
                    BookingStatus.CANCELLED,
                    endPlus,
                    startMinus
            );
            if (conflict) {
                result.addError("You need a gap of 24 hours between bookings.");
            }
        }
    }
}
