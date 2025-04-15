package com.gox.controller;

import com.gox.domain.entity.car.Car;
import com.gox.domain.exception.CarException;
import com.gox.domain.service.CarFacade;
import com.gox.mapper.CarMapper;
import com.gox.rest.api.CarsApi;
import com.gox.rest.dto.CarDto;
import com.gox.rest.dto.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
public class CarRestController implements CarsApi {
    private final CarFacade carFacade;
    private final CarMapper carMapper;

    public CarRestController(CarFacade carFacade, CarMapper carMapper) {
        this.carFacade = carFacade;
        this.carMapper = carMapper;
    }

    @Override
    public ResponseEntity<List<CarDto>> getCars() {
        List<Car> cars = carFacade.getAllCars();
        List<CarDto> carDtos = cars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDtos);
    }

    @Override
    public ResponseEntity<CarDto> getCarById(Long carId) {
        try {
            Car car = carFacade.get(carId);
            CarDto carDto = carMapper.toDto(car);
            return ResponseEntity.ok(carDto);
        } catch (CarException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<List<ReviewDto>> getReviewsForCar(Integer carId) {
        return null;
    }
}
