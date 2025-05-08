package com.gox.jpa.adapter;

import com.gox.domain.entity.photo.Photo;
import com.gox.domain.repository.PhotoRepository;
import com.gox.jpa.repository.PhotoSpringDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaPhotoRepositoryAdapter implements PhotoRepository {
    private final PhotoSpringDataRepository sd;

    public JpaPhotoRepositoryAdapter(PhotoSpringDataRepository sd) {
        this.sd = sd;
    }

    @Override
    public Photo create(Photo p) {
        return sd.save(p);
    }

    @Override
    public Photo read(Long id) {
        return sd.findById(id).orElse(null);
    }

    @Override
    public Photo update(Photo p) {
        return sd.save(p);
    }

    @Override
    public void delete(Long id) {
        sd.deleteById(id);
    }

    @Override
    public List<Photo> findByCarId(Long carId) {
        return sd.findByCarId(carId);
    }

    @Override
    public Photo findPreviewByCarId(Long carId) {
        return sd.findFirstByCarIdAndIsPreviewTrue(carId);
    }
}
