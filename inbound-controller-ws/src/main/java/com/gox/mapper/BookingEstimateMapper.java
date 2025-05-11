package com.gox.mapper;


import com.gox.domain.vo.BookingEstimate;
import com.gox.rest.dto.BookingEstimateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingEstimateMapper {

    BookingEstimateDto toDto(BookingEstimate vo);

}