package com.gox.domain.service;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;

import java.util.List;

public interface BookingFacade {
    Booking create(Booking booking);
    Booking get(Long id);
    List<Booking> getAll();
    void changeStatus(Long id, BookingStatus newStatus);
}