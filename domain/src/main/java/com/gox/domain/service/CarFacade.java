package com.gox.domain.service;

import com.gox.domain.entity.Car;

import java.util.List;

public interface CarFacade {
    Car get(Long id);
    List<Car> getAll();
    Car create(Car car);
    void delete(Long id);
}