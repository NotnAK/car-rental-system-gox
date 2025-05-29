package com.gox.mapper;

import com.gox.domain.entity.photo.Photo;
import com.gox.rest.dto.PhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PhotoMapper {

    @Mapping(source="preview", target="isPreview")
    @Mapping(target="url", expression="java(makeUrl(photo))")
    PhotoDto toDto(Photo photo);


    default String makeUrl(Photo photo) {
        return "/api/photos/" + photo.getId();
    }
}