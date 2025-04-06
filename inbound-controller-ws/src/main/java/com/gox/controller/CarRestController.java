package com.gox.controller;

import com.gox.domain.entity.Car;
import com.gox.domain.service.CarFacade;
import com.gox.mapper.CarMapper;
import com.gox.rest.api.CarsApi;
import com.gox.rest.dto.CarDto;
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
    public ResponseEntity<CarDto> createCar(CarDto carDto) {
        Car car = carMapper.toEntity(carDto);
        Car created = carFacade.create(car);
        return ResponseEntity.status(201).body(carMapper.toDto(created));
    }


    @Override
    public ResponseEntity<List<CarDto>> getCars() {
        List<Car> cars = carFacade.getAll();
        List<CarDto> carDtos = cars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDtos);
    }
}
