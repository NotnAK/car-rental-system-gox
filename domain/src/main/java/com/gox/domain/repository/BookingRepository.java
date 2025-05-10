package com.gox.domain.repository;

import com.gox.domain.entity.booking.Booking;

import java.util.List;

public interface BookingRepository {
    Booking create(Booking booking);
    Booking read(Long id);
    List<Booking> findAll();
    Booking update(Booking booking);
}
