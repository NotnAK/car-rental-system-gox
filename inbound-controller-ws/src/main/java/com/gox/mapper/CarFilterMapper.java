package com.gox.mapper;

import com.gox.domain.vo.CarFilter;
import com.gox.rest.dto.CarFilterRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarFilterMapper {
    @Mapping(target = "brand",        source = "brand")
    @Mapping(target = "year",         source = "year")
    @Mapping(target = "priceMin",     source = "priceMin")
    @Mapping(target = "priceMax",     source = "priceMax")
    @Mapping(target = "category",     source = "category")
    @Mapping(target = "seats",        source = "seats")
    @Mapping(target = "transmission", source = "transmission")
    @Mapping(target = "fuelType",     source = "fuelType")
    @Mapping(target = "sortBy", source = "sortBy")
    @Mapping(target = "sortDir",     source = "sortDir")
    CarFilter toVo(CarFilterRequestDto dto);
}
