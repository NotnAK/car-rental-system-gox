package com.gox.mapper;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.review.Review;
import com.gox.rest.dto.ReviewCreateRequestDto;
import com.gox.rest.dto.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Review toEntity(ReviewCreateRequestDto reviewCreateRequestDto);

    @Mapping(source = "car.id", target = "carId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "createdAt", target = "createdAt")
    ReviewDto toDto(Review review);


}
