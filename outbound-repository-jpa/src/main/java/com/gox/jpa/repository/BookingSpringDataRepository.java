package com.gox.jpa.repository;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookingSpringDataRepository extends JpaRepository<Booking, Long> {

    /**
     * Находит все брони по машине и списку статусов.
     * (используется для availability-эндпоинта)
     */
    List<Booking> findByCarIdAndStatusInAndEndDateAfter(Long carId, List<BookingStatus> statuses, OffsetDateTime endDateAfter);

    /**
     * Проверяет, пересекается ли любая бронь (status != excludedStatus)
     * с интервалом [startMinusGap … endPlusGap].
     */
/*    @Query("""
      SELECT CASE WHEN COUNT(b)>0 THEN true ELSE false END
      FROM Booking b
      WHERE b.car.id = :carId
        AND b.status <> :excludedStatus
        AND b.startDate <= :endPlusGap
        AND b.endDate   >= :startMinusGap
      """)
    boolean existsConflict(
            @Param("carId") Long carId,
            @Param("excludedStatus") BookingStatus excludedStatus,
            @Param("endPlusGap") OffsetDateTime endPlusGap,
            @Param("startMinusGap") OffsetDateTime startMinusGap
    );*/
    @Query("""
  SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
  FROM Booking b
  WHERE b.car.id = :carId
    AND b.status <> :excludedStatus
    AND b.startDate <= :endPlusGap
    AND COALESCE(b.actualReturnDate, b.endDate) >= :startMinusGap
  """)
    boolean existsConflict(
            @Param("carId") Long carId,
            @Param("excludedStatus") BookingStatus excludedStatus,
            @Param("endPlusGap") OffsetDateTime endPlusGap,
            @Param("startMinusGap") OffsetDateTime startMinusGap
    );
/*    List<Booking> findByCarIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long carId,
            BookingStatus excludedStatus,
            OffsetDateTime endPlusGap,
            OffsetDateTime startMinusGap
    );*/
}
