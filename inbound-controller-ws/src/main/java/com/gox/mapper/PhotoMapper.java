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

    /**
     * Собирает entity из отдельных параметров uploadPhoto( name, isPreview ).
     * Поле content и связь car заполняется вручную в контроллере.
     */
    @Named("fromParams")
    default Photo toEntity(String name, Boolean isPreview) {
        Photo p = new Photo();
        p.setName(name);
        p.setPreview(isPreview);
        return p;
    }

    /** Маппит entity → DTO (id, name, isPreview, url) */
    @Mapping(source="preview", target="isPreview")
    @Mapping(target="url", expression="java(makeUrl(photo))")
    PhotoDto toDto(Photo photo);

    /** Вспомогательный метод: возвращает строковый URL */
    default String makeUrl(Photo photo) {
        return "/api/photos/" + photo.getId();
    }
}