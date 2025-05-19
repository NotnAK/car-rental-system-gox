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
    private final List<ValidationRule<BookingValidationContext>> completionValidationRules;
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
                new MaxDurationRule(),
                new LeadTimeRule(),
                new GapRule(bookingRepository)
        );
        this.completionValidationRules = List.of(
                new MustBeApprovedRule(),
                new ActualReturnAfterStartRule()
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
        // 1) если уже COMPLETED или CANCELLED — дальше менять статус нельзя
        if (b.getStatus() == BookingStatus.COMPLETED || b.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingValidationException(
                    "Cannot change status of a " + b.getStatus() + " booking");
        }
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
        BookingValidationContext bookingValidationContext = BookingValidationContext.builder()
                .carId(carId)
                .pickupLocationId(pickupLocationId)
                .dropoffLocationId(dropoffLocationId)
                .user(user)
                .start(start)
                .end(end)
                .build();

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
        // 1) Проверка, что машина существует
        Car car = carRepo.read(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }

        // 2) Граница «месяц назад»
        OffsetDateTime oneMonthAgo = OffsetDateTime.now().minusMonths(2);

        // 3) Делаем запрос: только APPROVED и COMPLETED, запланированное endDate ≥ месяц назад
        List<Booking> bookings = bookingRepository
                .findByCarIdAndStatusInAndEndDateAfter(
                        carId,
                        List.of(BookingStatus.APPROVED, BookingStatus.COMPLETED),
                        oneMonthAgo
                );

        // 4) Мапим в VO, для COMPLETED используем actualReturnDate, если он раньше
        return bookings.stream()
                .map(b -> {
                    OffsetDateTime effectiveEnd;
                    if (b.getStatus() == BookingStatus.COMPLETED
                            && b.getActualReturnDate() != null
                            && b.getActualReturnDate().isBefore(b.getEndDate())) {
                        // вернули раньше — берём фактическую
                        effectiveEnd = b.getActualReturnDate();
                    } else {
                        // иначе — запланированную
                        effectiveEnd = b.getEndDate();
                    }
                    // добавляем сутки на техобслуживание
                    return new BookingInterval(
                            b.getStartDate(),
                            effectiveEnd
                    );
                })
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
        BigDecimal transferFee = BigDecimal.ZERO;

        if (!pickupLocationId.equals(dropoffLocationId) && urgent) {
            transferFee = URGENT_TRANSFER_FEE;
        }

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

    @Override
    public Booking completeBooking(Long bookingId, OffsetDateTime actualReturnDate) {
        Booking b = get(bookingId);
        BookingValidationContext ctx = BookingValidationContext.builder()
                .status(b.getStatus())
                .actualReturnDate(actualReturnDate)
                .start(b.getStartDate())
                .build();

        ValidationResult vr = new ValidationResult();
        for (ValidationRule<BookingValidationContext> rule : completionValidationRules) {
            rule.validate(ctx, vr);
        }
        if (vr.hasErrors()) {
            throw new BookingValidationException(vr.getCombinedMessage());
        }
        b.setActualReturnDate(actualReturnDate);

        // расчёт штрафа: опоздание > 30 мин
        Duration late = Duration.between(b.getEndDate(), actualReturnDate);
        if (late.toMinutes() > 30) {
            long hoursLate = (late.toMinutes() + 59) / 60; // округляем в большую сторону
            BigDecimal daily = b.getCar().getPricePerDay();
            BigDecimal penalty = daily
                    .multiply(BigDecimal.valueOf(0.4))     // 40%
                    .multiply(BigDecimal.valueOf(hoursLate));
            b.setPenalty(penalty);
        } else {
            b.setPenalty(BigDecimal.ZERO);
        }

        b.setStatus(BookingStatus.COMPLETED);
        return bookingRepository.update(b);
    }
}