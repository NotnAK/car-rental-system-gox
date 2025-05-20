package com.gox.domain.service;

import com.gox.domain.entity.car.*;
import com.gox.domain.entity.location.Location;
import com.gox.domain.exception.CarValidationException;
import com.gox.domain.exception.LocationNotFoundException;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;
import com.gox.domain.validation.car.rules.CarBrandNotEmptyRule;
import com.gox.domain.validation.car.rules.CarModelNotEmptyRule;
import com.gox.domain.validation.car.rules.CarNotNullRule;
import com.gox.domain.validation.car.rules.CarPricePositiveRule;

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
                new CarNotNullRule(),
                new CarBrandNotEmptyRule(),
                new CarModelNotEmptyRule(),
                new CarPricePositiveRule()
        );
    }

    public Car createCar(Car car, Long locationId) {
        // 1) проверяем поля доменной машины
        var ctx = CarValidationContext.builder().car(car).build();
        var vr  = new ValidationResult();
        for (var r : rules) r.validate(ctx, vr);
        if (vr.hasErrors()) throw new CarValidationException(vr.getCombinedMessage());

        // 2) подтягиваем локацию
        Location loc = locRepo.read(locationId);
        if (loc == null) throw new LocationNotFoundException("Location not found with id: " + locationId);
        car.setLocation(loc);

        // 3) сохраняем
        return carRepo.create(car);
    }
}