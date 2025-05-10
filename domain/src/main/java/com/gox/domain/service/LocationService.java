package com.gox.domain.service;

import com.gox.domain.entity.location.Location;
import com.gox.domain.exception.LocationNotFoundException;
import com.gox.domain.exception.LocationValidationException;
import com.gox.domain.repository.LocationRepository;

import java.util.List;
public class LocationService implements LocationFacade {
    private final LocationRepository repo;

    public LocationService(LocationRepository repo) {
        this.repo = repo;
    }

    @Override
    public Location create(Location location) {
        if (location.getCity() == null || location.getCity().isBlank() ||
                location.getStreet() == null || location.getStreet().isBlank()) {
            throw new LocationValidationException("City and street must be provided");
        }
        return repo.create(location);
    }

    @Override
    public Location get(Long id) {
        return repo.read(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    @Override
    public List<Location> getAll() {
        return repo.findAll();
    }

    @Override
    public Location update(Location location) {
        if (location.getId() == null || !repo.read(location.getId()).isPresent()) {
            throw new LocationNotFoundException(location.getId());
        }
        if (location.getCity() == null || location.getCity().isBlank() ||
                location.getStreet() == null || location.getStreet().isBlank()) {
            throw new LocationValidationException("City and street must be provided");
        }
        return repo.update(location);
    }

    @Override
    public void delete(Long id) {
        if (!repo.read(id).isPresent()) {
            throw new LocationNotFoundException(id);
        }
        repo.delete(id);
    }
}