package com.gox.domain.service;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.car.CarCategory;
import com.gox.domain.entity.car.FuelType;
import com.gox.domain.entity.car.TransmissionType;
import com.gox.domain.exception.CarNotFoundException;
import com.gox.domain.exception.CarValidationException;
import com.gox.domain.repository.CarFilterOptionsRepository;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.vo.CarFilter;
import com.gox.domain.vo.CarFilterOptions;

import java.math.BigDecimal;
import java.util.List;

public class CarService implements CarFacade {

    private final CarRepository carRepository;
    private final CarFilterOptionsRepository filterOptionsRepository;
    public CarService(CarRepository carRepository, CarFilterOptionsRepository filterOptionsRepository) {
        this.carRepository = carRepository;
        this.filterOptionsRepository = filterOptionsRepository;
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

}
