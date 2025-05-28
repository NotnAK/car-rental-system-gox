package com.gox.domain.service;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.photo.Photo;
import com.gox.domain.exception.CarNotFoundException;
import com.gox.domain.exception.ReviewValidationException;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.PhotoRepository;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;
import com.gox.domain.validation.photo.rules.*;

import java.util.List;


public class PhotoFactory {
    private final PhotoRepository photoRepo;
    private final CarRepository carRepo;
    private final List<ValidationRule<PhotoValidationContext>> createRules;

    public PhotoFactory(PhotoRepository photoRepo, CarRepository carRepo) {
        this.photoRepo = photoRepo;
        this.carRepo = carRepo;
        this.createRules     = List.of(
                new PhotoNameNotNullRule(),
                new PhotoNameNotEmptyRule(),
                new PhotoPreviewNotNullRule(),
                new PhotoPreviewUniqueRule(photoRepo),
                new PhotoContentNotNullRule(),
                new PhotoContentNotEmptyRule()
        );
    }

    public Photo create(Long carId,  String name, Boolean isPreview,  byte[] content) {
        Car car = carRepo.read(carId);
        if (car == null) throw new CarNotFoundException("Car not found: " + carId);
        var ctx = PhotoValidationContext.builder()
                .carId(carId)
                .name(name)
                .isPreview(isPreview)
                .content(content)
                .build();
        var vr  = new ValidationResult();
        for (var rule : createRules) {
            rule.validate(ctx, vr);
        }
        if (vr.hasErrors()) {
            throw new ReviewValidationException(vr.getCombinedMessage());
        }
        Photo photo = new Photo();
        photo.setName(name);
        photo.setCar(car);
        photo.setContent(content);
        photo.setPreview(isPreview);
        return photoRepo.create(photo);
    }
}