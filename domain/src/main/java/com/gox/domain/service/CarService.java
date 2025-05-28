package com.gox.domain.service;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.location.Location;
import com.gox.domain.exception.CarNotFoundException;
import com.gox.domain.exception.CarValidationException;
import com.gox.domain.exception.LocationNotFoundException;
import com.gox.domain.repository.CarFilterOptionsRepository;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;
import com.gox.domain.validation.car.rules.*;
import com.gox.domain.vo.CarFilter;
import com.gox.domain.vo.CarFilterOptions;

import java.util.List;

public class CarService implements CarFacade {

    private final CarRepository carRepository;
    private final CarFilterOptionsRepository filterOptionsRepository;
    private final LocationRepository locationRepository;
    private final List<ValidationRule<CarValidationContext>> rules;
    public CarService(CarRepository carRepository, CarFilterOptionsRepository filterOptionsRepository, LocationRepository locationRepository) {
        this.carRepository = carRepository;
        this.filterOptionsRepository = filterOptionsRepository;
        this.locationRepository = locationRepository;
        this.rules = List.of(
                new BrandNotNullRule(), new BrandNotBlankRule(), new DescriptionNotNullRule(),
                new DescriptionNotBlankRule(), new LocationIdNotNullRule(), new ModelNotNullRule(),
                new ModelNotBlankRule(), new PricePerDayNotNullRule(), new PricePerDayPositiveRule(),
                new SeatsRangeRule(), new YearRangeRule()
        );
    }

    @Override
    public Car get(Long id) {
        if (id == null || id <= 0) {
            throw new CarValidationException("Car ID must be positive");
        }
        Car car = carRepository.read(id);
        if (car == null) {
            throw new CarNotFoundException("Car with ID " + id + " not found");
        }
        return car;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new CarValidationException("Invalid ID for deletion");
        }
        Car existing = carRepository.read(id);
        if (existing == null) {
            throw new CarNotFoundException("Cannot delete non-existing car with ID " + id);
        }
        carRepository.delete(id);
    }

    @Override
    public List<Car> searchCars(CarFilter filter) {
        return carRepository.findByFilter(filter);
    }


    @Override
    public CarFilterOptions getFilterOptions() {
        return filterOptionsRepository.getFilterOptions();
    }
    @Override
    public Car update(Car car, Long locationId) {
        var ctx = CarValidationContext.builder().car(car).location(locationId).build();
        var vr = new ValidationResult();
        for (var rule : rules) {
            rule.validate(ctx, vr);
        }
        if (vr.hasErrors()) {
            throw new CarValidationException(vr.getCombinedMessage());
        }
        Location loc = locationRepository.read(locationId);
        if (loc == null) {
            throw new LocationNotFoundException("Location not found with id: " + locationId);
        }

        car.setLocation(loc);
        return carRepository.update(car);
    }

}
