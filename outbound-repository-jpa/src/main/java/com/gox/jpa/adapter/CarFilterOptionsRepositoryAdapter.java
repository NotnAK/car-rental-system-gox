package com.gox.jpa.adapter;


import com.gox.domain.repository.CarFilterOptionsRepository;
import com.gox.domain.vo.CarFilterOptions;
import com.gox.jpa.repository.CarSpringDataRepository;
import com.gox.domain.entity.car.CarCategory;
import com.gox.domain.entity.car.TransmissionType;
import com.gox.domain.entity.car.FuelType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class CarFilterOptionsRepositoryAdapter implements CarFilterOptionsRepository {
    private final CarSpringDataRepository carSpringDataRepository;

    public CarFilterOptionsRepositoryAdapter(CarSpringDataRepository carSpringDataRepository) {
        this.carSpringDataRepository = carSpringDataRepository;
    }

    @Override
    public CarFilterOptions getFilterOptions() {
        return new CarFilterOptions(
                carSpringDataRepository.findDistinctBrands(),
                carSpringDataRepository.findDistinctYears(),
                carSpringDataRepository.findMinPrice(),
                carSpringDataRepository.findMaxPrice(),
                Arrays.asList(CarCategory.values()),
                carSpringDataRepository.findDistinctSeats(),
                Arrays.asList(TransmissionType.values()),
                Arrays.asList(FuelType.values())
        );
    }
}
