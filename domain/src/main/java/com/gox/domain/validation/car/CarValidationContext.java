package com.gox.domain.validation.car;

import com.gox.domain.entity.car.Car;

public class CarValidationContext {
    private final Car car;
    private final Long locationId;

    private CarValidationContext(Builder builder) {
        this.car = builder.car;
        this.locationId = builder.locationId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Car getCar() {
        return car;
    }

    public Long getLocationId() {
        return locationId;
    }

    public static class Builder {
        private Car car;
        private Long locationId;
        public Builder car(Car car) {
            this.car = car;
            return this;
        }
        public Builder location(Long locationId) {
            this.locationId = locationId;
            return this;
        }
        public CarValidationContext build() {
            return new CarValidationContext(this);
        }
    }
}