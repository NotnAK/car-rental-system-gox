package com.gox.domain.service;

import com.gox.domain.entity.Car;
import com.gox.domain.repository.CarRepository;
import java.util.List;

public class CarService implements CarFacade {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car get(Long id) {
        return carRepository.read(id);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Car create(Car car) {
        return carRepository.create(car);
    }

    @Override
    public void delete(Long id) {
        carRepository.delete(id);
    }
}
