package com.gox.domain.service;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.location.Location;
import com.gox.domain.entity.user.User;
import com.gox.domain.exception.BookingValidationException;
import com.gox.domain.exception.CarNotFoundException;
import com.gox.domain.exception.LocationNotFoundException;
import com.gox.domain.repository.BookingRepository;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.vo.BookingEstimate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class BookingFactory {

    private final CarRepository carRepository;
    private final LocationRepository locationRepository;
    private final BookingFacade bookingFacade;
    private final BookingRepository bookingRepository;

    public BookingFactory(BookingFacade bookingFacade,
                          CarRepository carRepository,
                          LocationRepository locationRepository,
                          BookingRepository bookingRepository) {
        this.bookingFacade      = bookingFacade;
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Создаёт и сохраняет новое бронирование.
     * @throws BookingValidationException если даты/данные некорректны
     * @throws CarNotFoundException если carId не найден
     * @throws LocationNotFoundException если любая локация не найдена
     */
    public Booking createBooking(Long carId,
                                 Long pickupLocationId,
                                 Long dropoffLocationId,
                                 User user,
                                 OffsetDateTime start,
                                 OffsetDateTime end) {

        BookingEstimate est = bookingFacade.estimate(
                carId,
                pickupLocationId,
                dropoffLocationId,
                user,
                start,
                end
        );
        Car car = carRepository.read(carId);
        Location pickup = locationRepository.read(pickupLocationId);
        Location dropoff = locationRepository.read(dropoffLocationId);
        // --- Собираем бронирование ---
        Booking b = new Booking();
        b.setUser(user);
        b.setCar(car);
        b.setPickupLocation(pickup);
        b.setDropoffLocation(dropoff);
        b.setStartDate(start);
        b.setEndDate(end);
        b.setStatus(BookingStatus.PENDING);

        b.setUrgent(est.isUrgent());
        b.setTransferFee(est.getTransferFee());
        b.setTotalPrice(est.getTotalPrice());
        b.setPenalty(BigDecimal.ZERO); // штраф в расчёте не нужен на момент создания

        // --- Сохраняем ---
        return bookingRepository.create(b);
    }
}
