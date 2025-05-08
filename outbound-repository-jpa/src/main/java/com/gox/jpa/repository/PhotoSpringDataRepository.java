package com.gox.jpa.repository;

import com.gox.domain.entity.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoSpringDataRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByCarId(Long carId);
    Photo findFirstByCarIdAndIsPreviewTrue(Long carId);
}
