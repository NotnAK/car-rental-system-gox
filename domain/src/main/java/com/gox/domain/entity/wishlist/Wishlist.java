package com.gox.domain.entity.wishlist;

import com.gox.domain.entity.car.Car;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private Long id;
    private List<Car> cars = new ArrayList<>();

    public Wishlist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car) {
        if (car != null && !cars.contains(car)) {
            cars.add(car);
        }
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }
}
