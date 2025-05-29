package com.gox.domain.service;

import com.gox.domain.entity.location.Location;
import com.gox.domain.exception.LocationNotFoundException;
import com.gox.domain.exception.LocationValidationException;
import com.gox.domain.repository.BookingRepository;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.validation.ValidationExecutor;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.location.LocationValidationContext;
import com.gox.domain.validation.location.rules.*;

import java.util.List;
public class LocationService implements LocationFacade {
    private final LocationRepository locationRepository;
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    private final List<ValidationRule<LocationValidationContext>> validationRules;

    public LocationService(LocationRepository locationRepository,
                           CarRepository carRepository,
                           BookingRepository bookingRepository) {
        this.locationRepository = locationRepository;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
        this.validationRules = List.of(
                new LocationCityNotNullRule(),
                new LocationCityNotBlankRule(),
                new LocationStreetNotNullRule(),
                new LocationStreetNotBlankRule(),
                new LocationLatitudeNotNullRule(),
                new LocationLatitudeValidRangeRule(),
                new LocationLongitudeNotNullRule(),
                new LocationLongitudeValidRangeRule()
        );
    }

    @Override
    public Location create(Location location) {
        LocationValidationContext ctx = LocationValidationContext.builder()
                .location(location)
                .build();
        ValidationExecutor.validateOrThrow(
                ctx,
                validationRules,
                LocationValidationException::new
        );
        return locationRepository.create(location);
    }

    @Override
    public Location get(Long id) {
        Location location = locationRepository.read(id);
        if (location == null) {
            throw new LocationNotFoundException("Location not found with id: " + id);
        }
        return location;
    }

    @Override
    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location update(Location location) {
        if (location.getId() == null || locationRepository.read(location.getId()) == null) {
            throw new LocationNotFoundException("Location not found with id: " + location.getId());
        }

        LocationValidationContext ctx = LocationValidationContext.builder()
                .location(location)
                .build();
        ValidationExecutor.validateOrThrow(
                ctx,
                validationRules,
                LocationValidationException::new
        );
        return locationRepository.update(location);
    }

    @Override
    public void delete(Long id) {
        Location location = locationRepository.read(id);
        if (location == null) {
            throw new LocationNotFoundException("Location not found with id: " + id);
        }
        bookingRepository.nullifyDropoffLocationInBookings(location.getId());
        bookingRepository.nullifyPickupLocationInBookings(location.getId());
        carRepository.nullifyLocationInCars(location.getId());
        locationRepository.delete(id);
    }
}