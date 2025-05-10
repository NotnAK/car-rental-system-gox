package com.gox.domain.service;

import com.gox.domain.entity.location.Location;
import java.util.List;

public interface LocationFacade {
    Location create(Location location);
    Location get(Long id);
    List<Location> getAll();
    Location update(Location location);
    void delete(Long id);
}