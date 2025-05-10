package com.gox.jpa.adapter;

import com.gox.domain.entity.location.Location;
import com.gox.domain.repository.LocationRepository;
import com.gox.jpa.repository.LocationSpringDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepositoryAdapter implements LocationRepository {
    private final LocationSpringDataRepository springDataLocationRepository;

    public LocationRepositoryAdapter(LocationSpringDataRepository springDataLocationRepository) {
        this.springDataLocationRepository = springDataLocationRepository;
    }

    @Override
    public Location create(Location location) {
        return springDataLocationRepository.save(location);
    }

    @Override
    public Optional<Location> read(Long id) {
        return springDataLocationRepository.findById(id);
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