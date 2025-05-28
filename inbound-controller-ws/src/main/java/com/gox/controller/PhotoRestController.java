package com.gox.controller;

import com.gox.domain.entity.photo.Photo;
import com.gox.domain.service.PhotoFacade;
import com.gox.mapper.PhotoMapper;
import com.gox.rest.api.PhotosApi;
import com.gox.rest.dto.PhotoDto;
import com.gox.rest.dto.PhotoUpdateRequestDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoRestController implements PhotosApi {

    private final PhotoFacade photoFacade;
    private final PhotoMapper photoMapper;

    public PhotoRestController(PhotoFacade photoFacade,
                               PhotoMapper photoMapper) {
        this.photoFacade = photoFacade;
        this.photoMapper = photoMapper;
    }

    @Override
    public ResponseEntity<Resource> getPhotoContent(Long photoId) {
        Photo p = photoFacade.get(photoId);
        ByteArrayResource resource = new ByteArrayResource(p.getContent());
        return ResponseEntity.ok()
                // inline – чтобы браузер сразу отобразил картинку
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + p.getName() + "\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
    @Override
    public ResponseEntity<Void> deletePhoto(Long photoId) {
        photoFacade.delete(photoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PhotoDto> updatePhoto(
            Long photoId,
            PhotoUpdateRequestDto dto) {

        Photo updated = photoFacade.update(
                photoId,
                dto.getName(),
                dto.getIsPreview()
        );
        return ResponseEntity.ok(photoMapper.toDto(updated));
    }
}