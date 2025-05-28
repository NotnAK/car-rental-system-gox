package com.gox.domain.service;

import com.gox.domain.entity.photo.Photo;
import com.gox.domain.entity.car.Car;
import com.gox.domain.exception.*;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.PhotoRepository;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;
import com.gox.domain.validation.photo.rules.PhotoContentNotEmptyRule;
import com.gox.domain.validation.photo.rules.PhotoNameNotEmptyRule;
import com.gox.domain.validation.photo.rules.PhotoNotNullRule;
import com.gox.domain.validation.photo.rules.PhotoPreviewUniqueRule;

import java.util.List;

public class PhotoService implements PhotoFacade {
    private final PhotoRepository photoRepo;
    private final CarRepository carRepo;
    private final List<ValidationRule<PhotoValidationContext>> rules;
    public PhotoService(PhotoRepository photoRepo, CarRepository carRepo) {
        this.photoRepo = photoRepo;
        this.carRepo = carRepo;
        this.rules     = List.of(
                new PhotoNotNullRule(),
                new PhotoNameNotEmptyRule(),
                new PhotoContentNotEmptyRule(),
                new PhotoPreviewUniqueRule(photoRepo)
        );
    }

    @Override
    public Photo create(Long carId, Photo photo) {
        Car car = carRepo.read(carId);
        if (car == null) throw new CarNotFoundException("Car not found: " + carId);
        var ctx = PhotoValidationContext.builder()
                .carId(carId)
                .photo(photo)
                .build();
        var vr = new ValidationResult();
        rules.forEach(r -> r.validate(ctx, vr));
        if (vr.hasErrors()) {
            throw new PhotoValidationException(vr.getCombinedMessage());
        }

        // 3) Сохраняем
        photo.setCar(car);
        return photoRepo.create(photo);
    }

    @Override
    public List<Photo> getAllForCar(Long carId) {
        return photoRepo.findByCarId(carId);
    }

    @Override
    public Photo getPreviewForCar(Long carId) {
        Photo p = photoRepo.findPreviewByCarId(carId);
        return p;
    }

    @Override
    public Photo get(Long id) {
        Photo p = photoRepo.read(id);
        if (p == null) throw new PhotoNotFoundException("Photo not found: " + id);
        return p;
    }

    @Override
    public void delete(Long id) {
        Photo p = photoRepo.read(id);
        if (p == null) throw new PhotoNotFoundException("Photo not found: " + id);
        photoRepo.delete(id);
    }
}
