package com.gox.controller;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.photo.Photo;
import com.gox.domain.entity.user.User;
import com.gox.domain.service.*;
import com.gox.domain.vo.CarFilter;
import com.gox.domain.vo.CarFilterOptions;
import com.gox.mapper.*;
import com.gox.rest.api.CarsApi;
import com.gox.rest.dto.*;
import com.gox.security.CurrentUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CarRestController implements CarsApi {
    private final CarFactory   carFactory;
    private final CarFacade carFacade;
    private final CarMapper carMapper;
    private final ReviewFacade reviewFacade;
    private final ReviewMapper reviewMapper;
    private final PhotoFacade photoFacade;
    private final PhotoFactory photoFactory;
    private final PhotoMapper photoMapper;
    private final CurrentUserDetailService currentUserDetailService;
    private final ReviewFactory reviewFactory;
    private final BookingFacade bookingFacade;
    private final BookingIntervalMapper intervalMapper;
    private final CarFilterMapper filterMapper;
    private final CarFilterOptionsMapper carFilterOptionsMapper;
    public CarRestController(CarFactory carFactory,
                             CarFacade carFacade,
                             CarMapper carMapper,
                             BookingFacade bookingFacade,
                             BookingIntervalMapper intervalMapper,
                             ReviewFacade reviewFacade,
                             ReviewMapper reviewMapper,
                             PhotoFacade photoFacade,
                             PhotoFactory photoFactory,
                             PhotoMapper photoMapper,
                             CurrentUserDetailService currentUserDetailService,
                             ReviewFactory reviewFactory,
                             CarFilterMapper filterMapper,
                             CarFilterOptionsMapper carFilterOptionsMapper) {
        this.carFactory = carFactory;
        this.carFacade = carFacade;
        this.carMapper = carMapper;
        this.reviewFacade = reviewFacade;
        this.reviewMapper = reviewMapper;
        this.photoFacade = photoFacade;
        this.photoFactory = photoFactory;
        this.photoMapper = photoMapper;
        this.currentUserDetailService = currentUserDetailService;
        this.reviewFactory = reviewFactory;
        this.bookingFacade    = bookingFacade;
        this.intervalMapper   = intervalMapper;
        this.filterMapper = filterMapper;
        this.carFilterOptionsMapper = carFilterOptionsMapper;
    }

    @Override
    public ResponseEntity<List<CarDto>> getCars(
            CarFilterRequestDto filterDto
    ) {
        CarFilter filter = filterMapper.toVo(filterDto);
        List<Car> cars = carFacade.searchCars(filter);
        List<CarDto> dtos = cars.stream()
                .map(car -> {
                    CarDto dto = carMapper.toDto(car);
                    Photo previewEntity = photoFacade.getPreviewForCar(car.getId());
                    PhotoDto previewDto = photoMapper.toDto(previewEntity);
                    dto.setPreview(previewDto);
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @Override
    public ResponseEntity<CarDto> createCar(CarCreateRequestDto dto) {
        Car carEntity = carMapper.toEntity(dto);
        Car saved = carFactory.createCar(carEntity, dto.getLocationId());
        return ResponseEntity.status(201).body(carMapper.toDto(saved));
    }
    @Override
    public ResponseEntity<String> createReview(Integer carId,
                                               ReviewCreateRequestDto dto) {
        // Получаем залогиненного пользователя
        User currentUser = currentUserDetailService.getFullCurrentUser();
        // Создаём отзыв в доменном слое, передаём carId из path-а
        reviewFactory.createReview(
                carId.longValue(),
                currentUser,
                dto.getRating(),
                dto.getComment()
        );
        // Возвращаем 201 Created без тела
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<CarDto> getCarById(Long carId) {
        Car car = carFacade.get(carId);
        CarDto dto = carMapper.toDto(car);

        // Получаем и мапим превью
        Photo previewEntity = photoFacade.getPreviewForCar(carId);
        PhotoDto previewDto = photoMapper.toDto(previewEntity);
        dto.setPreview(previewDto);

        // (опционально) все фото
        List<PhotoDto> photos = photoFacade.getAllForCar(carId).stream()
                .map(photoMapper::toDto)
                .collect(Collectors.toList());
        dto.setPhotos(photos);

        return ResponseEntity.ok(dto);
    }


    @Override
    public ResponseEntity<List<ReviewDto>> getReviewsForCar(Integer carId) {
        List<ReviewDto> dtos = reviewFacade.getByCarId(carId.longValue()).stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @Override
    public ResponseEntity<PhotoDto> uploadPhoto(Long carId,
                                                String name,
                                                Boolean isPreview,
                                                MultipartFile file) {
        try {
            Photo saved = photoFactory.create(carId, name, isPreview,(file != null)?file.getBytes():null);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(photoMapper.toDto(saved));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
    public ResponseEntity<List<BookingIntervalDto>> getBusyIntervals(Long carId) {
        List<BookingIntervalDto> dto = bookingFacade.getBusyIntervals(carId).stream()
                .map(intervalMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CarFilterOptionsDto> getCarFilterOptions() {
        CarFilterOptions vo = carFacade.getFilterOptions();
        return ResponseEntity.ok(carFilterOptionsMapper.toDto(vo));
    }

    @Override
    public ResponseEntity<CarDto> updateCar(Long carId, CarUpdateRequestDto carUpdateRequestDto) {
        Car carEntity = carMapper.toEntity(carUpdateRequestDto);
        carEntity.setId(carId);
        Car saved = carFacade.update(carEntity, carUpdateRequestDto.getLocationId());
        return ResponseEntity.ok(carMapper.toDto(saved));
    }
    @Override
    public ResponseEntity<Void> deleteCar(Long carId) {
        carFacade.delete(carId);
        return ResponseEntity.noContent().build();
    }
}
