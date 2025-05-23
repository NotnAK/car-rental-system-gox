package com.gox.domain.vo;

import com.gox.domain.entity.car.CarCategory;
import com.gox.domain.entity.car.FuelType;
import com.gox.domain.entity.car.TransmissionType;

import java.math.BigDecimal;
import java.util.List;

public class CarFilterOptions {
    private final List<String> brands;
    private final List<Integer> years;
    private final BigDecimal priceMin;
    private final BigDecimal priceMax;
    private final List<CarCategory> categories;
    private final List<Integer> seats;
    private final List<TransmissionType> transmissions;
    private final List<FuelType> fuelTypes;

    public CarFilterOptions(
            List<String> brands,
            List<Integer> years,
            BigDecimal priceMin,
            BigDecimal priceMax,
            List<CarCategory> categories,
            List<Integer> seats,
            List<TransmissionType> transmissions,
            List<FuelType> fuelTypes) {
        this.brands = brands;
        this.years = years;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.categories = categories;
        this.seats = seats;
        this.transmissions = transmissions;
        this.fuelTypes = fuelTypes;
    }
    public List<String> getBrands() {
        return brands;
    }

    public List<Integer> getYears() {
        return years;
    }

    public BigDecimal getPriceMin() {
        return priceMin;
    }

    public BigDecimal getPriceMax() {
        return priceMax;
    }

    public List<CarCategory> getCategories() {
        return categories;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public List<TransmissionType> getTransmissions() {
        return transmissions;
    }

    public List<FuelType> getFuelTypes() {
        return fuelTypes;
    }
}
