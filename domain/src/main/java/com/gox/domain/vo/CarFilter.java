// src/main/java/com/gox/domain/vo/CarFilter.java
package com.gox.domain.vo;

import com.gox.domain.entity.car.CarCategory;
import com.gox.domain.entity.car.TransmissionType;
import com.gox.domain.entity.car.FuelType;
import java.math.BigDecimal;

public class CarFilter {
    private final String brand;
    private final Integer year;
    private final BigDecimal priceMin;
    private final BigDecimal priceMax;
    private final CarCategory category;
    private final Integer seats;
    private final TransmissionType transmission;
    private final FuelType fuelType;
    private final String sortBy;
    private final String sortDir;
    public CarFilter(String brand,
                     Integer year,
                     BigDecimal priceMin,
                     BigDecimal priceMax,
                     CarCategory category,
                     Integer seats,
                     TransmissionType transmission,
                     FuelType fuelType,
                     String sortBy,
                     String sortDir) {
        this.brand        = brand;
        this.year         = year;
        this.priceMin     = priceMin;
        this.priceMax     = priceMax;
        this.category     = category;
        this.seats        = seats;
        this.transmission = transmission;
        this.fuelType     = fuelType;
        this.sortBy       = sortBy;
        this.sortDir      = sortDir;
    }
    public String getBrand()               { return brand; }
    public Integer getYear()               { return year; }
    public BigDecimal getPriceMin()        { return priceMin; }
    public BigDecimal getPriceMax()        { return priceMax; }
    public CarCategory getCategory()       { return category; }
    public Integer getSeats()              { return seats; }
    public TransmissionType getTransmission() { return transmission; }
    public FuelType getFuelType()          { return fuelType; }
    public String getSortBy() {
        return sortBy;
    }
    public String getSortDir() {
        return sortDir;
    }
}
