package com.gox.domain.repository;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookingRepository {
    Booking create(Booking booking);
    Booking read(Long id);
    Booking update(Booking booking);
    void delete(Long id);
    void deleteByUserId(Long userId);
    List<Booking> findAll();
    List<Booking> findByUserId(Long userId);
    boolean existsConflict(Long carId,
                           BookingStatus excludedStatus,
                           OffsetDateTime endPlusGap,
                           OffsetDateTime startMinusGap);
   List<Booking> findByCarIdAndStatusInAndEndDateAfter(
           Long carId,
           List<BookingStatus> statuses,
           OffsetDateTime endDateAfter
   );
    List<Booking> findByUserIdAndStatus(Long userId, BookingStatus status);
    void nullifyPickupLocationInBookings(Long locationId);
    void nullifyDropoffLocationInBookings(Long locationId);
    void nullifyCar(Long carId);
}
