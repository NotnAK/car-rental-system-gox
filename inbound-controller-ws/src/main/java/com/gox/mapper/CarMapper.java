package com.gox.mapper;

import com.gox.domain.entity.car.Car;
import com.gox.rest.dto.CarCreateRequestDto;
import com.gox.rest.dto.CarDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    // При преобразовании Car → CarDto говорим MapStruct:
    // - не заполнять preview
    // - не заполнять photos
    @Mapping(target = "preview", ignore = true)
    @Mapping(target = "photos", ignore = true)
    CarDto toDto(Car car);
    // из запроса создания
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    Car toEntity(CarCreateRequestDto dto);
    @Mapping(target = "location", ignore = true)
    Car toEntity(CarDto dto);
}
