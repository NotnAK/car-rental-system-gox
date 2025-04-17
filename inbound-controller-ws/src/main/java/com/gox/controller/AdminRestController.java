package com.gox.controller;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.user.User;
import com.gox.domain.service.CarFacade;
import com.gox.domain.service.ReviewFacade;
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
    private final CarFacade carFacade;
    private final CarMapper carMapper;
    private final ReviewFacade reviewFacade;

    public AdminRestController(UserFacade userFacade,
                               UserMapper userMapper,
                               CarFacade carFacade,
                               CarMapper carMapper,
                               ReviewFacade reviewFacade) {
        this.userFacade = userFacade;
        this.userMapper = userMapper;
        this.carFacade = carFacade;
        this.carMapper = carMapper;
        this.reviewFacade = reviewFacade;
    }
    @Override
    public ResponseEntity<CarDto> createCar(CarDto carDto) {
        Car car = carMapper.toEntity(carDto);
        Car created = carFacade.create(car);
        return ResponseEntity.status(201).body(carMapper.toDto(created));
    }

    @Override
    public ResponseEntity<Void> adminDeleteReview(Integer reviewId) {
        reviewFacade.deleteReview(reviewId.longValue());
        return ResponseEntity.ok().build();
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
