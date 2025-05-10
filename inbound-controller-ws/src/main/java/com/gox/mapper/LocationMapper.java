package com.gox.mapper;

import com.gox.domain.entity.location.Location;
import com.gox.rest.dto.LocationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDto toDto(Location loc);
    Location toEntity(LocationDto dto);
}
