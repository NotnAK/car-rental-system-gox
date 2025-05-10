package com.gox.jpa.adapter;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.repository.BookingRepository;
import com.gox.jpa.repository.BookingSpringDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaBookingRepositoryAdapter implements BookingRepository {

    private final BookingSpringDataRepository jpaRepo;

    public JpaBookingRepositoryAdapter(BookingSpringDataRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Booking create(Booking booking) {
        return jpaRepo.save(booking);
    }

    @Override
    public Booking read(Long id) {
        return jpaRepo.findById(id).orElse(null);
    }

    @Override
    public List<Booking> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public Booking update(Booking booking) {
        return jpaRepo.save(booking);
    }
}