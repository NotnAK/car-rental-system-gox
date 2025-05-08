package com.gox.domain.service;

import com.gox.domain.entity.photo.Photo;
import com.gox.domain.entity.car.Car;
import com.gox.domain.exception.*;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.PhotoRepository;
import java.util.List;

public class PhotoService implements PhotoFacade {
    private final PhotoRepository photoRepo;
    private final CarRepository carRepo;

    public PhotoService(PhotoRepository photoRepo, CarRepository carRepo) {
        this.photoRepo = photoRepo;
        this.carRepo = carRepo;
    }

    @Override
    public Photo upload(Long carId, Photo photo) {
        if (photo == null || photo.getName()==null || photo.getName().isBlank()
                || photo.getContent()==null || photo.getContent().length==0)
            throw new PhotoValidationException("Invalid photo data");
        Car car = carRepo.read(carId);
        if (car == null) throw new CarNotFoundException("Car not found: "+carId);
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
        if (p==null) throw new PhotoNotFoundException("Photo not found: "+id);
        return p;
    }

    @Override
    public void delete(Long id) {
        Photo p = photoRepo.read(id);
        if (p==null) throw new PhotoNotFoundException("Photo not found: "+id);
        photoRepo.delete(id);
    }
}
