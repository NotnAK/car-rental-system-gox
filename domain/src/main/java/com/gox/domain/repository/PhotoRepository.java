package com.gox.domain.repository;

import com.gox.domain.entity.photo.Photo;
import java.util.List;

public interface PhotoRepository {
    Photo create(Photo photo);
    Photo read(Long id);
    Photo update(Photo photo);
    void delete(Long id);
    List<Photo> findByCarId(Long carId);
    Photo findPreviewByCarId(Long carId);
}