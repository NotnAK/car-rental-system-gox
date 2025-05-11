package com.gox.jpa.adapter;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.repository.BookingRepository;
import com.gox.jpa.repository.BookingSpringDataRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
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

/*    @Override
    public boolean existsConflict(Long carId,
                                  BookingStatus excludedStatus,
                                  OffsetDateTime endPlusGap,
                                  OffsetDateTime startMinusGap) {
        return jpaRepo.existsConflict(carId, excludedStatus, endPlusGap, startMinusGap);
    }*/
    @Override
    public List<Booking> findByCarIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long carId,
                                                                                                    BookingStatus excludedStatus,
                                                                                                    OffsetDateTime endPlusGap,
                                                                                                    OffsetDateTime startMinusGap) {
        return jpaRepo.findByCarIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(carId, excludedStatus, endPlusGap, startMinusGap);
    }

    @Override
    public List<Booking> findByCarIdAndStatusIn(Long carId, List<BookingStatus> statuses) {
        return jpaRepo.findByCarIdAndStatusIn(carId, statuses);
    }
}