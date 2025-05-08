package com.gox.controller;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.photo.Photo;
import com.gox.domain.exception.CarValidationException;
import com.gox.domain.service.CarFacade;
import com.gox.domain.service.PhotoFacade;
import com.gox.domain.service.ReviewFacade;
import com.gox.mapper.CarMapper;
import com.gox.mapper.PhotoMapper;
import com.gox.mapper.ReviewMapper;
import com.gox.rest.api.CarsApi;
import com.gox.rest.dto.CarDto;
import com.gox.rest.dto.PhotoDto;
import com.gox.rest.dto.ReviewDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CarRestController implements CarsApi {
    private final CarFacade carFacade;
    private final CarMapper carMapper;
    private final ReviewFacade reviewFacade;
    private final ReviewMapper reviewMapper;
    private final PhotoFacade photoFacade;
    private final PhotoMapper photoMapper;

    public CarRestController(CarFacade carFacade,
                             CarMapper carMapper,
                             ReviewFacade reviewFacade,
                             ReviewMapper reviewMapper,
                             PhotoFacade photoFacade,
                             PhotoMapper photoMapper) {
        this.carFacade = carFacade;
        this.carMapper = carMapper;
        this.reviewFacade = reviewFacade;
        this.reviewMapper = reviewMapper;
        this.photoFacade = photoFacade;
        this.photoMapper = photoMapper;
    }

    @Override
    public ResponseEntity<List<CarDto>> getCars() {
        List<CarDto> dtos = carFacade.getAllCars().stream()
                .map(car -> {
                    CarDto dto = carMapper.toDto(car);
                    // Получаем entity
                    Photo previewEntity = photoFacade.getPreviewForCar(car.getId());
                    // Конвертируем в DTO
                    PhotoDto previewDto = photoMapper.toDto(previewEntity);
                    dto.setPreview(previewDto);
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
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
            // 1) Собираем entity
            Photo p = photoMapper.toEntity(name, isPreview);
            // 2) Наполняем контент и привязываем к carId в service
            p.setContent(file.getBytes());
            // 3) Сохраняем
            Photo saved = photoFacade.upload(carId, p);
            // 4) Возвращаем DTO
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(photoMapper.toDto(saved));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
