// PhotoPreviewUniqueRule.java
package com.gox.domain.validation.photo.rules;

import com.gox.domain.exception.PhotoValidationException;
import com.gox.domain.repository.PhotoRepository;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;
import com.gox.domain.entity.photo.Photo;

public class PhotoPreviewUniqueRule implements ValidationRule<PhotoValidationContext> {
    private final PhotoRepository photoRepo;

    public PhotoPreviewUniqueRule(PhotoRepository repo) {
        this.photoRepo = repo;
    }

    @Override
    public void validate(PhotoValidationContext ctx, ValidationResult result) {
        if (ctx.getPreview()!=null && Boolean.TRUE.equals(ctx.getPreview())) {
            Photo existing = photoRepo.findPreviewByCarId(ctx.getCarId());
            if (existing != null) {
                result.addError("A preview for this car already exists");
            }
        }
    }
}
