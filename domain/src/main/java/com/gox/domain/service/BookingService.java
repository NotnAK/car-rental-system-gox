package com.gox.domain.service;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.user.User;
import com.gox.domain.exception.*;
import com.gox.domain.repository.BookingRepository;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.repository.UserRepository;
import com.gox.domain.service.booking.BookingBusyIntervalProvider;
import com.gox.domain.service.booking.BookingCompletionHandler;
import com.gox.domain.service.booking.BookingEstimateCalculator;
import com.gox.domain.service.booking.BookingLoyaltyUpdater;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.booking.BookingValidationContext;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.booking.rules.*;
import com.gox.domain.vo.BookingEstimate;
import com.gox.domain.vo.BookingInterval;

import java.time.OffsetDateTime;
import java.util.List;

public class BookingService implements BookingFacade {
    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final LocationRepository locationRepository;
    private final BookingBusyIntervalProvider bookingBusyIntervalProvider;
    private final BookingEstimateCalculator bookingEstimateCalculator = new BookingEstimateCalculator();
    private final BookingCompletionHandler bookingCompletionHandler;
    private final BookingLoyaltyUpdater bookingLoyaltyUpdater;
    private final UserRepository userRepository;
    private final List<ValidationRule<BookingValidationContext>> validationRules;
    private final List<ValidationRule<BookingValidationContext>> completionValidationRules;
    public BookingService(BookingRepository bookingRepository,
                          CarRepository carRepository,
                          LocationRepository locationRepository,
                          BookingBusyIntervalProvider bookingBusyIntervalProvider,
                          BookingCompletionHandler bookingCompletionHandler,
                          BookingLoyaltyUpdater bookingLoyaltyUpdater,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
        this.bookingBusyIntervalProvider = bookingBusyIntervalProvider;
        this.bookingCompletionHandler = bookingCompletionHandler;
        this.bookingLoyaltyUpdater = bookingLoyaltyUpdater;
        this.userRepository = userRepository;
        this.validationRules = List.of(
                new CarIdNotNullRule(),
                new PickupLocationIdNotNullRule(),
                new DropoffLocationIdNotNullRule(),
                new DatesNotNullRule(),
                new StartBeforeEndRule(),
                new MaxDurationRule(),
                new LeadTimeRule(),
                new GapRule(bookingRepository)
        );
        this.completionValidationRules = List.of(
                new MustBeApprovedRule(),
                new ActualReturnNotNullRule(),
                new ActualReturnAfterStartRule()
        );
    }

    @Override
    public Booking get(Long id) {
        Booking b = bookingRepository.read(id);
        if (b == null) {
            throw new BookingNotFoundException("Booking not found: " + id);
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
        Car car = carRepository.read(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
        if (locationRepository.read(pickupLocationId) == null) {
            throw new LocationNotFoundException("Pickup location not found with id: " + pickupLocationId);
        }
        if (locationRepository.read(dropoffLocationId) == null) {
            throw new LocationNotFoundException("Dropoff location not found with id: " + dropoffLocationId);
        }
        return bookingEstimateCalculator.calculate(car, user, start, end, pickupLocationId, dropoffLocationId);
    }
    @Override
    public List<BookingInterval> getBusyIntervals(Long carId) {
        if (carRepository.read(carId) == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
        return bookingBusyIntervalProvider.getIntervals(carId);
    }


    @Override
    public Booking completeBooking(Long bookingId, OffsetDateTime actualReturnDate) {
        Booking booking = get(bookingId);
        BookingValidationContext ctx = BookingValidationContext.builder()
                .status(booking.getStatus())
                .actualReturnDate(actualReturnDate)
                .start(booking.getStartDate())
                .build();
        ValidationResult vr = new ValidationResult();
        for (ValidationRule<BookingValidationContext> rule : completionValidationRules) {
            rule.validate(ctx, vr);
        }
        if (vr.hasErrors()) {
            throw new BookingValidationException(vr.getCombinedMessage());
        }
        Booking updated = bookingCompletionHandler.complete(booking, actualReturnDate);
        bookingLoyaltyUpdater.updateLoyalty(updated.getUser());
        return updated;
    }
    public List<Booking> getByUserId(Long userId) {
        if (userRepository.read(userId) == null) {
            throw new CarNotFoundException("User with ID " + userId + " not found");
        }
        return bookingRepository.findByUserId(userId);
    }
    public void deleteByUserId(Long userId) {
        bookingRepository.deleteByUserId(userId);
    }
    public void delete(Long bookingId) {
        if (bookingId == null || bookingId <= 0) {
            throw new BookingValidationException("Invalid booking ID");
        }
        Booking existing = bookingRepository.read(bookingId);
        if (existing == null) {
            throw new BookingNotFoundException("Booking not found: " + bookingId);
        }
        bookingRepository.delete(bookingId);
    }
}