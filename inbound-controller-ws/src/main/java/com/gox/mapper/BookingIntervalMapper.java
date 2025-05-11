package com.gox.mapper;


import com.gox.domain.vo.BookingInterval;
import com.gox.rest.dto.BookingIntervalDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingIntervalMapper {
    BookingIntervalDto toDto(BookingInterval vo);
}