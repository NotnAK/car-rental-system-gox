package com.gox.jpa.adapter;

import com.gox.domain.entity.photo.Photo;
import com.gox.domain.repository.PhotoRepository;
import com.gox.jpa.repository.PhotoSpringDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaPhotoRepositoryAdapter implements PhotoRepository {
    private final PhotoSpringDataRepository photoSpringDataRepository;

    public JpaPhotoRepositoryAdapter(PhotoSpringDataRepository photoSpringDataRepository) {
        this.photoSpringDataRepository = photoSpringDataRepository;
    }

    @Override
    public Photo create(Photo p) {
        return photoSpringDataRepository.save(p);
    }

    @Override
    public Photo read(Long id) {
        return photoSpringDataRepository.findById(id).orElse(null);
    }

    @Override
    public Photo update(Photo p) {
        return photoSpringDataRepository.save(p);
    }

    @Override
    public void delete(Long id) {
        photoSpringDataRepository.deleteById(id);
    }

    @Override
    public List<Photo> findByCarId(Long carId) {
        return photoSpringDataRepository.findByCarId(carId);
    }

    @Override
    public Photo findPreviewByCarId(Long carId) {
        return photoSpringDataRepository.findFirstByCarIdAndIsPreviewTrue(carId);
    }
    public void deleteByCarId(Long carId){
        photoSpringDataRepository.deleteById(carId);
    }

}
