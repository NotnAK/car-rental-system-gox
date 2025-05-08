package com.gox.domain.service;
import com.gox.domain.entity.photo.Photo;
import java.util.List;

public interface PhotoFacade {
    Photo upload(Long carId, Photo photo);
    List<Photo> getAllForCar(Long carId);
    Photo getPreviewForCar(Long carId);
    Photo get(Long id);
    void delete(Long id);
}