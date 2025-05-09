package com.gox.controller;

import com.gox.domain.entity.photo.Photo;
import com.gox.domain.service.PhotoFacade;
import com.gox.rest.api.PhotosApi;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoRestController implements PhotosApi {

    private final PhotoFacade photoFacade;

    public PhotoRestController(PhotoFacade photoFacade) {
        this.photoFacade = photoFacade;
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
}