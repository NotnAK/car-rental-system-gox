package com.gox.domain.validation.car;

import com.gox.domain.entity.car.Car;

public class CarValidationContext {
    private final Car car;

    private CarValidationContext(Builder builder) {
        this.car = builder.car;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Car getCar() {
        return car;
    }

    public static class Builder {
        private Car car;

        public Builder car(Car car) {
            this.car = car;
            return this;
        }

        public CarValidationContext build() {
            return new CarValidationContext(this);
        }
    }
}