package com.gox.controller;

import com.gox.domain.entity.location.Location;
import com.gox.domain.service.LocationFacade;
import com.gox.mapper.LocationMapper;
import com.gox.rest.api.LocationsApi;
import com.gox.rest.dto.LocationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LocationRestController implements LocationsApi {

    private final LocationFacade facade;
    private final LocationMapper mapper;

    public LocationRestController(LocationFacade facade, LocationMapper mapper) {
        this.facade = facade;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<LocationDto> createLocation(LocationDto locationDto) {
        Location entity = mapper.toEntity(locationDto);
        Location created = facade.create(entity);
        return ResponseEntity
                .status(201)
                .body(mapper.toDto(created));
    }

    @Override
    public ResponseEntity<List<LocationDto>> getLocations() {
        List<LocationDto> dtos = facade.getAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<Void> deleteLocation(Integer locationId) {
        facade.delete(locationId.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<LocationDto> getLocationById(Integer locationId) {
        Location loc = facade.get(locationId.longValue());
        return ResponseEntity.ok(mapper.toDto(loc));
    }

    @Override
    public ResponseEntity<Void> updateLocation(Integer locationId, LocationDto locationDto) {
        Location entity = mapper.toEntity(locationDto);
        entity.setId(locationId.longValue());
        facade.update(entity);
        return ResponseEntity.ok().build();
    }
}