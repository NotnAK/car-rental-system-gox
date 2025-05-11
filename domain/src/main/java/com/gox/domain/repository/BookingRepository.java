package com.gox.domain.repository;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookingRepository {
    Booking create(Booking booking);
    Booking read(Long id);
    List<Booking> findAll();
    Booking update(Booking booking);
    //findByCarIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual
/*    boolean existsConflict(Long carId,
                           BookingStatus excludedStatus,
                           OffsetDateTime endPlusGap,
                           OffsetDateTime startMinusGap);*/
    List<Booking> findByCarIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long carId,
                                                                                             BookingStatus excludedStatus,
                                                                                             OffsetDateTime endPlusGap,
                                                                                             OffsetDateTime startMinusGap);
}
