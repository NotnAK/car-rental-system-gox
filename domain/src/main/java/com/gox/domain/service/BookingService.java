package com.gox.domain.service;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.exception.BookingNotFoundException;
import com.gox.domain.repository.BookingRepository;

import java.util.List;

public class BookingService implements BookingFacade {
    private final BookingRepository repo;

    public BookingService(BookingRepository repo) {
        this.repo = repo;
    }

    @Override
    public Booking create(Booking booking) {
        // TODO: в будущем здесь валидация и расчёты
        return repo.create(booking);
    }

    @Override
    public Booking get(Long id) {
        Booking b = repo.read(id);
        if (b == null) {
            throw new BookingNotFoundException(id);
        }
        return b;
    }

    @Override
    public List<Booking> getAll() {
        return repo.findAll();
    }

    @Override
    public void changeStatus(Long id, BookingStatus newStatus) {
        Booking b = get(id);
        b.setStatus(newStatus);
        repo.update(b);
    }
}