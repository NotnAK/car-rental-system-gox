package com.gox.domain.service;

import com.gox.domain.entity.photo.Photo;
import com.gox.domain.entity.car.Car;
import com.gox.domain.exception.*;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.PhotoRepository;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;
import com.gox.domain.validation.photo.rules.*;

import java.util.List;

public class PhotoService implements PhotoFacade {
    private final PhotoRepository photoRepo;
    private final List<ValidationRule<PhotoValidationContext>> updateRules;
    public PhotoService(PhotoRepository photoRepo) {
        this.photoRepo = photoRepo;
        this.updateRules     = List.of(
                new PhotoNameNotNullRule(),
                new PhotoNameNotEmptyRule(),
                new PhotoPreviewNotNullRule(),
                new PhotoPreviewUniqueRule(photoRepo)
        );
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
    @Override
    public Photo update(Long id, String name, Boolean isPreview) {
        Photo photo = photoRepo.read(id);
        if(photo == null){
            throw new PhotoNotFoundException("Photo not found: " + id);
        }

        var ctx = PhotoValidationContext.builder()
                .carId(photo.getCar().getId())
                .name(name)
                .isPreview(isPreview)
                .build();
        var vr  = new ValidationResult();
        for (var rule : updateRules) {
            rule.validate(ctx, vr);
        }
        if (vr.hasErrors()) {
            throw new ReviewValidationException(vr.getCombinedMessage());
        }
        photo.setPreview(isPreview);
        photo.setName(name);
        return photoRepo.update(photo);
    }
}
