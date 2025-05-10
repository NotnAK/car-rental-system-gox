package com.gox.domain.repository;

import com.gox.domain.entity.location.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository {
    Location create(Location location);
    Optional<Location> read(Long id);
    List<Location> findAll();
    Location update(Location location);
    void delete(Long id);
}
