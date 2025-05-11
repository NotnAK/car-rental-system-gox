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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class BookingFactory {

    private final CarRepository carRepository;
    private final LocationRepository locationRepository;
    private final BookingRepository bookingRepository;

    public BookingFactory(CarRepository carRepository,
                          LocationRepository locationRepository,
                          BookingRepository bookingRepository) {
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
                                 OffsetDateTime startDate,
                                 OffsetDateTime endDate) {

        // --- Проверки входных данных ---
        if (user == null) {
            throw new BookingValidationException("User must not be null");
        }
        if (carId == null) {
            throw new BookingValidationException("Car ID is required");
        }
        if (startDate == null || endDate == null) {
            throw new BookingValidationException("Start and end dates are required");
        }
        if (!startDate.isBefore(endDate)) {
            throw new BookingValidationException("Start date must be before end date");
        }

        // --- Загрузка связанных сущностей ---
        Car car = carRepository.read(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
        Location pickup = locationRepository.read(pickupLocationId);
        if (pickup == null) {
            throw new LocationNotFoundException("Pickup location not found: " + pickupLocationId);
        }

        Location dropoff = locationRepository.read(dropoffLocationId);
        if (dropoff == null) {
            throw new LocationNotFoundException("Dropoff location not found: " + dropoffLocationId);
        }

        // --- Собираем бронирование ---
        Booking b = new Booking();
        b.setUser(user);
        b.setCar(car);
        b.setPickupLocation(pickup);
        b.setDropoffLocation(dropoff);
        b.setStartDate(startDate);
        b.setEndDate(endDate);
        b.setStatus(BookingStatus.PENDING);

        // --- Базовые вычисления (пока простые) ---
        // Расчёт срочности: меньше 10 ч до старта?
        long hoursUntilStart = Duration.between(LocalDateTime.now(), startDate).toHours();
        b.setUrgent(hoursUntilStart < 10);

        // Пока оставляем в 0 – в сервисе пойдут более сложные расчёты
        b.setTransferFee(BigDecimal.ZERO);
        b.setTotalPrice(BigDecimal.ZERO);
        b.setPenalty(BigDecimal.ZERO);

        // --- Сохраняем ---
        return bookingRepository.create(b);
    }
}
