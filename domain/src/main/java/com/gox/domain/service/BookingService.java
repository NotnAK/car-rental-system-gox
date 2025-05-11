package com.gox.domain.service;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.user.User;
import com.gox.domain.exception.BookingNotFoundException;
import com.gox.domain.exception.BookingValidationException;
import com.gox.domain.exception.CarNotFoundException;
import com.gox.domain.exception.LocationNotFoundException;
import com.gox.domain.repository.BookingRepository;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.vo.BookingEstimate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class BookingService implements BookingFacade {
    private final BookingRepository repo;
    private final CarRepository carRepo;
    private final LocationRepository locRepo;
    private static final BigDecimal URGENT_TRANSFER_FEE = new BigDecimal("30");
    public BookingService(BookingRepository repo,
                          CarRepository carRepo,
                          LocationRepository locRepo) {
        this.repo    = repo;
        this.carRepo = carRepo;
        this.locRepo = locRepo;
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

    @Override
    public BookingEstimate estimate(Long carId,
                                    Long pickupLocationId,
                                    Long dropoffLocationId,
                                    User user,
                                    OffsetDateTime start,
                                    OffsetDateTime end) {
        // --- 1) Базовые проверки и загрузка ---
        Car car = carRepo.read(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
        locRepo.read(pickupLocationId)
                .orElseThrow(() -> new LocationNotFoundException("Pickup location not found: " + pickupLocationId));
        locRepo.read(dropoffLocationId)
                .orElseThrow(() -> new LocationNotFoundException("Dropoff location not found: " + dropoffLocationId));
        if (start.isAfter(end) || start.isEqual(end)) {
            throw new BookingValidationException("Start must be strictly before end");
        }

        OffsetDateTime now = OffsetDateTime.now();

        // --- 2) Минимальный lead time: не раньше, чем через 4 часа от "сейчас" ---
        OffsetDateTime earliest = now.plusHours(4);
        if (start.isBefore(earliest)) {
            throw new BookingValidationException(
                    "You must book at least 4 hours in advance; earliest possible start is " + earliest);
        }

        // --- 3) Между бронями обязателен разрыв не менее 24 часов ---
        // Берём все активные (не отменённые) брони на эту машину
        boolean conflict = !repo
                .findByCarIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        carId,
                        BookingStatus.CANCELLED,
                        end.plusDays(1),
                        start.minusDays(1)
                ).isEmpty();
        if (conflict) {
            throw new BookingValidationException("You need a gap of 24 hours between bookings.");
        }

        // --- 4) Расчёт срочности и трансфера ---
        long hoursUntilStart = Duration.between(now, start).toHours();
        boolean urgent = hoursUntilStart < 10;  // срочно, если меньше 10 часов
        BigDecimal transferFee = pickupLocationId.equals(dropoffLocationId)
                ? BigDecimal.ZERO
                : (urgent ? URGENT_TRANSFER_FEE : BigDecimal.ZERO);

        // --- 5) Оплата по суткам (округление вперёд) ---
        long seconds = Duration.between(start, end).getSeconds();
        long days = (seconds + 86_400 - 1) / 86_400; // ceil по суткам

        BigDecimal basePrice = car.getPricePerDay()
                .multiply(BigDecimal.valueOf(days));
        double loyaltyRate = switch (user.getLoyaltyLevel()) {
            case SILVER -> 0.05;
            case GOLD   -> 0.10;
            default     -> 0.0;
        };
        BigDecimal loyaltyDiscount = basePrice.multiply(BigDecimal.valueOf(loyaltyRate));
        BigDecimal discountedPrice = basePrice.subtract(loyaltyDiscount);
        BigDecimal totalPrice = discountedPrice.add(transferFee);

        return new BookingEstimate(
                (int) days,
                basePrice,
                loyaltyDiscount,
                discountedPrice,
                urgent,
                transferFee,
                totalPrice
        );
    }

}