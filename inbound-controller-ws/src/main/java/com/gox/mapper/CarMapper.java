package com.gox.mapper;

import com.gox.domain.entity.car.Car;
import com.gox.rest.dto.CarDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    Car toEntity(CarDto dto);

    CarDto toDto(Car car);


}
