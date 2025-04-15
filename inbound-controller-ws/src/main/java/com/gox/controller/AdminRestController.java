package com.gox.controller;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.user.User;
import com.gox.domain.service.CarFacade;
import com.gox.domain.service.UserFacade;
import com.gox.mapper.CarMapper;
import com.gox.mapper.UserMapper;
import com.gox.rest.api.AdminApi;
import com.gox.rest.dto.CarDto;
import com.gox.rest.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
public class AdminRestController implements AdminApi {
    private final UserFacade userFacade;
    private final UserMapper userMapper;
    private final CarMapper carMapper;
    private final CarFacade carFacade;

    public AdminRestController(UserFacade userFacade, UserMapper userMapper, CarMapper carMapper, CarFacade carFacade) {
        this.userFacade = userFacade;
        this.userMapper = userMapper;
        this.carMapper = carMapper;
        this.carFacade = carFacade;
    }
    @Override
    public ResponseEntity<CarDto> createCar(CarDto carDto) {
        Car car = carMapper.toEntity(carDto);
        Car created = carFacade.create(car);
        return ResponseEntity.status(201).body(carMapper.toDto(created));
    }

    @Override
    public ResponseEntity<Void> adminDeleteReview(Integer reviewId) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userFacade.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

}
