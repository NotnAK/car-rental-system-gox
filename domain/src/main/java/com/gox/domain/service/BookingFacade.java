package com.gox.domain.service;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.entity.user.User;
import com.gox.domain.vo.BookingEstimate;
import com.gox.domain.vo.BookingInterval;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookingFacade {
    Booking get(Long id);
    List<Booking> getAll();
    void changeStatus(Long id, BookingStatus newStatus);
    void deleteByUserId(Long userId);
    void delete(Long bookingId);
    BookingEstimate estimate(Long carId,
                             Long pickupLocationId,
                             Long dropoffLocationId,
                             Long carLocationId,
                             User user,
                             OffsetDateTime start,
                             OffsetDateTime end);
    List<Booking> getByUserId(Long userId);
    List<BookingInterval> getBusyIntervals(Long carId);
    Booking completeBooking(Long bookingId, OffsetDateTime actualReturnDate);
}