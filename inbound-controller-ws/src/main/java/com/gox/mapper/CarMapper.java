package com.gox.mapper;

import com.gox.domain.entity.car.Car;
import com.gox.rest.dto.CarCreateRequestDto;
import com.gox.rest.dto.CarDto;
import com.gox.rest.dto.CarUpdateRequestDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {
    @Mapping(target = "preview", ignore = true)
    @Mapping(target = "photos", ignore = true)
    CarDto toDto(Car car);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    Car toEntity(CarCreateRequestDto dto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    Car toEntity(CarUpdateRequestDto dto);


    @Mapping(target = "location", ignore = true)
    Car toEntity(CarDto dto);
}
