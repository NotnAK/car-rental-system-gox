package com.gox.jpa.adapter;

import com.gox.domain.entity.location.Location;
import com.gox.domain.repository.LocationRepository;
import com.gox.jpa.repository.LocationSpringDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaLocationRepositoryAdapter implements LocationRepository {
    private final LocationSpringDataRepository springDataLocationRepository;

    public JpaLocationRepositoryAdapter(LocationSpringDataRepository springDataLocationRepository) {
        this.springDataLocationRepository = springDataLocationRepository;
    }

    @Override
    public Location create(Location location) {
        return springDataLocationRepository.save(location);
    }

    @Override
    public Location read(Long id) {
        return springDataLocationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Location> findAll() {
        return springDataLocationRepository.findAll();
    }

    @Override
    public Location update(Location location) {
        return springDataLocationRepository.save(location);
    }

    @Override
    public void delete(Long id) {
        springDataLocationRepository.deleteById(id);
    }
}