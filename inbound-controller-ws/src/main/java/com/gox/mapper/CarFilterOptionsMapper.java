package com.gox.mapper;

import com.gox.domain.vo.CarFilterOptions;
import com.gox.rest.dto.CarFilterOptionsDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarFilterOptionsMapper {
    CarFilterOptionsDto toDto(CarFilterOptions vo);
}