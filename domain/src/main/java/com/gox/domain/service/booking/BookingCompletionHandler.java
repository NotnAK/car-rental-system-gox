package com.gox.domain.service.booking;
import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.repository.BookingRepository;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;

public class BookingCompletionHandler {
    private final BookingRepository bookingRepository;
    public BookingCompletionHandler(
            BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    public Booking complete(Booking booking, OffsetDateTime actualReturnDate) {
        booking.setActualReturnDate(actualReturnDate);
        Duration late = Duration.between(booking.getEndDate(), actualReturnDate);
        BigDecimal penalty = BigDecimal.ZERO;
        if (late.toMinutes() > 30) {
            long hoursLate = (late.toMinutes() + 59) / 60;
            BigDecimal daily = booking.getCar().getPricePerDay();
            penalty = daily
                    .multiply(BigDecimal.valueOf(0.4))
                    .multiply(BigDecimal.valueOf(hoursLate));
        }
        booking.setPenalty(penalty);
        booking.setTotalPrice(booking.getTotalPrice().add(penalty));

        booking.setStatus(BookingStatus.COMPLETED);
        return bookingRepository.update(booking);
    }
}
