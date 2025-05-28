package com.gox.domain.service;
import com.gox.domain.entity.photo.Photo;
import java.util.List;

public interface PhotoFacade {
    Photo update(Long photoId, String name, Boolean isPreview);
    List<Photo> getAllForCar(Long carId);
    Photo getPreviewForCar(Long carId);
    Photo get(Long id);
    void delete(Long id);
}