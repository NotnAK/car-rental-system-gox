package com.gox.jpa.repository;

import com.gox.domain.entity.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSpringDataRepository extends JpaRepository<Booking, Long> {
}
