package com.gox.domain.service.booking;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.entity.car.Car;
import com.gox.domain.exception.CarNotFoundException;
import com.gox.domain.repository.BookingRepository;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.vo.BookingInterval;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BookingBusyIntervalProvider {
    private final BookingRepository bookingRepository;

    public BookingBusyIntervalProvider(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingInterval> getIntervals(Long carId) {
        OffsetDateTime oneMonthAgo = OffsetDateTime.now().minusMonths(2);
        List<Booking> bookings = bookingRepository
                .findByCarIdAndStatusInAndEndDateAfter(
                        carId,
                        List.of(BookingStatus.APPROVED, BookingStatus.COMPLETED, BookingStatus.PENDING),
                        oneMonthAgo
                );

        return bookings.stream()
                .map(b -> {
                    OffsetDateTime effectiveEnd;
                    if (b.getStatus() == BookingStatus.COMPLETED
                            && b.getActualReturnDate() != null
                            && b.getActualReturnDate().isBefore(b.getEndDate())) {
                        // вернули раньше — берём фактическую
                        effectiveEnd = b.getActualReturnDate();
                    } else {
                        // иначе — запланированную
                        effectiveEnd = b.getEndDate();
                    }
                    // добавляем сутки на техобслуживание
                    return new BookingInterval(
                            b.getStartDate(),
                            effectiveEnd
                    );
                })
                .collect(Collectors.toList());
    }
}