package com.gox.domain.service;

import com.gox.domain.entity.car.*;
import com.gox.domain.entity.location.Location;
import com.gox.domain.exception.LocationNotFoundException;
import com.gox.domain.exception.ReviewValidationException;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.validation.ValidationExecutor;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;
import com.gox.domain.validation.car.rules.*;
import java.util.List;

public class CarFactory {

    private final CarRepository carRepo;
    private final LocationRepository locRepo;
    private final List<ValidationRule<CarValidationContext>> rules;

    public CarFactory(CarRepository carRepo,
                      LocationRepository locRepo) {
        this.carRepo = carRepo;
        this.locRepo = locRepo;
        this.rules = List.of(
                new BrandNotNullRule(), new BrandNotBlankRule(), new DescriptionNotNullRule(),
                new DescriptionNotBlankRule(), new LocationIdNotNullRule(), new ModelNotNullRule(),
                new ModelNotBlankRule(), new PricePerDayNotNullRule(), new PricePerDayPositiveRule(),
                new SeatsRangeRule(), new YearRangeRule()
        );
    }

    public Car create(Car car, Long locationId) {
        var ctx = CarValidationContext.builder().car(car).location(locationId).build();
        ValidationExecutor.validateOrThrow(
                ctx,
                rules,
                ReviewValidationException::new
        );
        Location loc = locRepo.read(locationId);
        if (loc == null){
            throw new LocationNotFoundException("Location not found with id: " + locationId);
        }
        car.setLocation(loc);
        return carRepo.create(car);
    }
}