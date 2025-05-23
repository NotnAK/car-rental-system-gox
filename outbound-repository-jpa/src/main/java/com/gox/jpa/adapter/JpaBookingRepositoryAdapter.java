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

    private final BookingSpringDataRepository bookingSpringDataRepository;

    public JpaBookingRepositoryAdapter(BookingSpringDataRepository bookingSpringDataRepository) {
        this.bookingSpringDataRepository = bookingSpringDataRepository;
    }

    @Override
    public Booking create(Booking booking) {
        return bookingSpringDataRepository.save(booking);
    }

    @Override
    public Booking read(Long id) {
        return bookingSpringDataRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> findAll() {
        return bookingSpringDataRepository.findAll();
    }

    @Override
    public Booking update(Booking booking) {
        return bookingSpringDataRepository.save(booking);
    }

    @Override
    public boolean existsConflict(Long carId,
                                  BookingStatus excludedStatus,
                                  OffsetDateTime endPlusGap,
                                  OffsetDateTime startMinusGap) {
        return bookingSpringDataRepository.existsConflict(carId, excludedStatus, endPlusGap, startMinusGap);
    }
/*    @Override
    public List<Booking> findByCarIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long carId,
                                                                                                    BookingStatus excludedStatus,
                                                                                                    OffsetDateTime endPlusGap,
                                                                                                    OffsetDateTime startMinusGap) {
        return jpaRepo.findByCarIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(carId, excludedStatus, endPlusGap, startMinusGap);
    }*/
    @Override
    public List<Booking> findByCarIdAndStatusInAndEndDateAfter(
            Long carId,
            List<BookingStatus> statuses,
            OffsetDateTime endDateAfter){
        return bookingSpringDataRepository.findByCarIdAndStatusInAndEndDateAfter(carId, statuses, endDateAfter);
    }
/*    @Override
    public List<Booking> findByCarIdAndStatusIn(Long carId, List<BookingStatus> statuses) {
        return bookingSpringDataRepository.findByCarIdAndStatusIn(carId, statuses);
    }*/

    @Override
    public List<Booking> findByUserIdAndStatus(Long userId, BookingStatus status) {
        return bookingSpringDataRepository.findByUserIdAndStatus(userId, status);
    }
}