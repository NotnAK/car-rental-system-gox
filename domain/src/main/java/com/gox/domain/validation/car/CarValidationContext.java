package com.gox.domain.validation.car;

import com.gox.domain.entity.car.Car;

public class CarValidationContext {
    private final Car car;
    public CarValidationContext(Car car) { this.car = car; }
    public Car getCar() { return car; }
}