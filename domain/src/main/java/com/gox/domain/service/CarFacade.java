package com.gox.domain.service;

import com.gox.domain.entity.car.Car;

import java.util.List;

public interface CarFacade {
    Car get(Long id);
    List<Car> getAllCars();
    void delete(Long id);
}