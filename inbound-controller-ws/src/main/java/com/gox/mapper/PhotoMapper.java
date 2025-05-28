package com.gox.mapper;

import com.gox.domain.entity.photo.Photo;
import com.gox.rest.dto.PhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Mapper(componentModel = "spring")
public interface PhotoMapper {

    /** Маппит entity → DTO (id, name, isPreview, url) */
    @Mapping(source="preview", target="isPreview")
    @Mapping(target="url", expression="java(makeUrl(photo))")
    PhotoDto toDto(Photo photo);

    /** Вспомогательный метод: возвращает строковый URL */
    default String makeUrl(Photo photo) {
        return "/api/photos/" + photo.getId();
    }
}