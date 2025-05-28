package com.gox.domain.repository;

import com.gox.domain.entity.car.Car;
import com.gox.domain.vo.CarFilter;

import java.util.List;

public interface CarRepository {
    Car read(Long id);
    Car update(Car car);
    List<Car> findAll();
    Car create(Car car);
    void delete(Long id);
    List<Car> findByFilter(CarFilter filter);
}