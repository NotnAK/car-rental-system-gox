package com.gox.domain.repository;

import com.gox.domain.entity.car.Car;

import java.util.List;

public interface CarRepository {
    Car read(Long id);
    List<Car> findAll();
    Car create(Car car);
    void delete(Long id);
}