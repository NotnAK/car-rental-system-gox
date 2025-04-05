package com.gox.mapper;

import com.gox.domain.entity.Car;
import com.gox.domain.entity.CarState;
import com.gox.rest.dto.CarDto;
import com.gox.rest.dto.CarStateDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    Car toEntity(CarDto dto);

    CarDto toDto(Car car);


}
