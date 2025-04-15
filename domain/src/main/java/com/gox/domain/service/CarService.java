package com.gox.domain.service;

import com.gox.domain.entity.car.Car;
import com.gox.domain.exception.CarException;
import com.gox.domain.repository.CarRepository;

import java.math.BigDecimal;
import java.util.List;

public class CarService implements CarFacade {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car get(Long id) {
        if (id == null || id <= 0) {
            throw new CarException("Car ID must be positive");
        }
        Car car = carRepository.read(id);
        if (car == null) {
            throw new CarException("Car with ID " + id + " not found");
        }
        return car;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car create(Car car) {
        validateCar(car);
        return carRepository.create(car);
    }

    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new CarException("Invalid ID for deletion");
        }
        Car existing = carRepository.read(id);
        if (existing == null) {
            throw new CarException("Cannot delete non-existing car with ID " + id);
        }
        carRepository.delete(id);
    }
    private void validateCar(Car car) {
        if (car == null) {
            throw new CarException("Car must not be null");
        }
        if (car.getBrand() == null || car.getBrand().isBlank()) {
            throw new CarException("Car brand is required");
        }
        if (car.getModel() == null || car.getModel().isBlank()) {
            throw new CarException("Car model is required");
        }
        if (car.getPricePerDay() == null || car.getPricePerDay().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CarException("Price per day must be positive");
        }
    }
}
