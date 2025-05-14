package com.gox.domain.service;

import com.gox.domain.entity.car.Car;
import com.gox.domain.exception.CarNotFoundException;
import com.gox.domain.exception.CarValidationException;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.car.CarValidationContext;
import com.gox.domain.validation.car.rules.CarBrandNotEmptyRule;
import com.gox.domain.validation.car.rules.CarModelNotEmptyRule;
import com.gox.domain.validation.car.rules.CarNotNullRule;
import com.gox.domain.validation.car.rules.CarPricePositiveRule;

import java.math.BigDecimal;
import java.util.List;

public class CarService implements CarFacade {

    private final CarRepository carRepository;
    private final List<ValidationRule<CarValidationContext>> rules;
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
        this.rules = List.of(
                new CarNotNullRule(),
                new CarBrandNotEmptyRule(),
                new CarModelNotEmptyRule(),
                new CarPricePositiveRule()
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
    public Car create(Car car) {
        // 1) контекст + 2) прогоняем правила
        var ctx = new CarValidationContext(car);
        var vr  = new ValidationResult();
        for (var r : rules) {
            r.validate(ctx, vr);
        }
        if (vr.hasErrors()) {
            throw new CarValidationException(vr.getCombinedMessage());
        }
        // 3) если всё ок — сохраняем
        return carRepository.create(car);
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
/*    private void validateCar(Car car) {
        if (car == null) {
            throw new CarValidationException("Car must not be null");
        }
        if (car.getBrand() == null || car.getBrand().isBlank()) {
            throw new CarValidationException("Car brand is required");
        }
        if (car.getModel() == null || car.getModel().isBlank()) {
            throw new CarValidationException("Car model is required");
        }
        if (car.getPricePerDay() == null || car.getPricePerDay().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CarValidationException("Price per day must be positive");
        }
    }*/
}
