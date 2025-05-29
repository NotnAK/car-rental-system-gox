package com.gox.jpa.repository;

import com.gox.domain.entity.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PhotoSpringDataRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByCarId(Long carId);
    Photo findFirstByCarIdAndIsPreviewTrue(Long carId);
    @Transactional
    @Modifying
    void deleteByCarId(Long carId);
}
