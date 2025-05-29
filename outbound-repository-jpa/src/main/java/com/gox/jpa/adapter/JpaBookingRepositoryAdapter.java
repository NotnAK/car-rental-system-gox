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
        return bookingSpringDataRepository.findAllByOrderByIdDesc();
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

    @Override
    public List<Booking> findByUserId(Long userId) {
        return bookingSpringDataRepository.findByUserIdOrderByIdDesc(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        bookingSpringDataRepository.deleteByUserId(userId);
    }
    @Override
    public void nullifyPickupLocationInBookings(Long locationId) {
        bookingSpringDataRepository.nullifyPickupLocation(locationId);
    }

    @Override
    public void nullifyDropoffLocationInBookings(Long locationId) {
        bookingSpringDataRepository.nullifyDropoffLocation(locationId);
    }

    @Override
    public void nullifyCar(Long carId) {
        bookingSpringDataRepository.nullifyCar(carId);
    }

    @Override
    public void delete(Long id) {
        bookingSpringDataRepository.deleteById(id);
    }
}