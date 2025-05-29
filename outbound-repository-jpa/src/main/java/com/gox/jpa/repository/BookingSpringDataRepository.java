package com.gox.jpa.repository;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookingSpringDataRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCarIdAndStatusInAndEndDateAfter(Long carId, List<BookingStatus> statuses, OffsetDateTime endDateAfter);
    List<Booking> findByUserIdAndStatus(Long userId, BookingStatus status);
    List<Booking> findAllByOrderByIdDesc();
    @Query("""
  SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
  FROM Booking b
  WHERE b.car.id = :carId
    AND b.status <> :excludedStatus
    AND b.startDate < :endPlusGap
    AND (
      CASE
        WHEN b.actualReturnDate IS NOT NULL
             AND b.actualReturnDate < b.endDate
        THEN b.actualReturnDate
        ELSE b.endDate
      END
    ) > :startMinusGap
  """)
    boolean existsConflict(
            @Param("carId") Long carId,
            @Param("excludedStatus") BookingStatus excludedStatus,
            @Param("endPlusGap") OffsetDateTime endPlusGap,
            @Param("startMinusGap") OffsetDateTime startMinusGap
    );
    List<Booking> findByUserIdOrderByIdDesc(Long userId);
    @Modifying
    @Transactional
    void deleteByUserId(Long userId);
    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.pickupLocation = NULL WHERE b.pickupLocation.id = :locId")
    void nullifyPickupLocation(@Param("locId") Long locId);
    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.dropoffLocation = NULL WHERE b.dropoffLocation.id = :locId")
    void nullifyDropoffLocation(@Param("locId") Long locId);
    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.car = NULL WHERE b.car.id = :carId")
    void nullifyCar(@Param("carId") Long carId);
}
