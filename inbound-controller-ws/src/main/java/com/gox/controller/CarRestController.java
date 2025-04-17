package com.gox.controller;

import com.gox.domain.entity.car.Car;
import com.gox.domain.exception.CarValidationException;
import com.gox.domain.service.CarFacade;
import com.gox.domain.service.ReviewFacade;
import com.gox.mapper.CarMapper;
import com.gox.mapper.ReviewMapper;
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
    private final ReviewFacade reviewFacade;
    private final ReviewMapper reviewMapper;

    public CarRestController(CarFacade carFacade,
                             CarMapper carMapper,
                             ReviewFacade reviewFacade,
                             ReviewMapper reviewMapper) {
        this.carFacade = carFacade;
        this.carMapper = carMapper;
        this.reviewFacade = reviewFacade;
        this.reviewMapper = reviewMapper;
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
        Car car = carFacade.get(carId);
        CarDto carDto = carMapper.toDto(car);
        return ResponseEntity.ok(carDto);
    }


    @Override
    public ResponseEntity<List<ReviewDto>> getReviewsForCar(Integer carId) {
        List<ReviewDto> dtos = reviewFacade.getByCarId(carId.longValue()).stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
