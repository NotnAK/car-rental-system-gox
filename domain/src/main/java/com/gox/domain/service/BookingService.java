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
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.booking.BookingValidationContext;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.rules.*;
import com.gox.domain.vo.BookingEstimate;
import com.gox.domain.vo.BookingInterval;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService implements BookingFacade {
    private final BookingRepository bookingRepository;
    private final CarRepository carRepo;
    private final LocationRepository locRepo;
    private static final BigDecimal URGENT_TRANSFER_FEE = new BigDecimal("30");
    private final List<ValidationRule<BookingValidationContext>> validationRules;
    public BookingService(BookingRepository bookingRepository,
                          CarRepository carRepo,
                          LocationRepository locRepo) {
        this.bookingRepository = bookingRepository;
        this.carRepo = carRepo;
        this.locRepo = locRepo;
        this.validationRules = List.of(
                new UserNotNullRule(),
                new CarIdNotNullRule(),
                new PickupLocationIdNotNullRule(),
                new DropoffLocationIdNotNullRule(),
                new DateOrderRule(),
                new LeadTimeRule(),
                new GapRule(bookingRepository)
        );
    }

    @Override
    public Booking get(Long id) {
        Booking b = bookingRepository.read(id);
        if (b == null) {
            throw new BookingNotFoundException(id);
        }
        return b;
    }

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void changeStatus(Long id, BookingStatus newStatus) {
        Booking b = get(id);
        b.setStatus(newStatus);
        bookingRepository.update(b);
    }

    @Override
    public BookingEstimate estimate(Long carId,
                                    Long pickupLocationId,
                                    Long dropoffLocationId,
                                    User user,
                                    OffsetDateTime start,
                                    OffsetDateTime end) {
        BookingValidationContext bookingValidationContext = new BookingValidationContext(
                carId, pickupLocationId, dropoffLocationId, user, start, end
        );
        ValidationResult vr = new ValidationResult();
        for (ValidationRule<BookingValidationContext> rule : validationRules) {
            rule.validate(bookingValidationContext, vr);
        }
        if (vr.hasErrors()) {
            throw new BookingValidationException(vr.getCombinedMessage());
        }
        Car car = carRepo.read(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
        if (locRepo.read(pickupLocationId) == null) {
            throw new LocationNotFoundException("Pickup location not found with id: " + pickupLocationId);
        }
        if (locRepo.read(dropoffLocationId) == null) {
            throw new LocationNotFoundException("Dropoff location not found with id: " + dropoffLocationId);
        }
        return calculateEstimate(car, user, start, end, pickupLocationId, dropoffLocationId);
    }
    @Override
    public List<BookingInterval> getBusyIntervals(Long carId) {
        // проверка, что машина существует
        Car car = carRepo.read(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
        // получаем все APPROVED-брони
        List<Booking> bookings = bookingRepository.findByCarIdAndStatusIn(
                carId,
                List.of(BookingStatus.APPROVED)
        );

        // мапим в VO
        return bookings.stream()
                .map(b -> new BookingInterval(b.getStartDate(), b.getEndDate()))
                .collect(Collectors.toList());
    }

    private BookingEstimate calculateEstimate(
            Car car,
            User user,
            OffsetDateTime start,
            OffsetDateTime end,
            Long pickupLocationId,
            Long dropoffLocationId
    ) {
        OffsetDateTime now = OffsetDateTime.now();

        // срочность
        long hoursUntilStart = Duration.between(now, start).toHours();
        boolean urgent = hoursUntilStart < 10;
        BigDecimal transferFee = pickupLocationId.equals(dropoffLocationId)
                ? BigDecimal.ZERO
                : (urgent ? URGENT_TRANSFER_FEE : BigDecimal.ZERO);

        // дни (ceil)
        long seconds = Duration.between(start, end).getSeconds();
        long days    = (seconds + 86_400 - 1) / 86_400;

        // базовая цена и скидка
        BigDecimal basePrice = car.getPricePerDay().multiply(BigDecimal.valueOf(days));
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