package com.gox.domain.validation.photo.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;

public class PhotoContentNotEmptyRule implements ValidationRule<PhotoValidationContext> {
    @Override
    public void validate(PhotoValidationContext ctx, ValidationResult result) {
        var p = ctx.getPhoto();
        if (p != null && (p.getContent() == null || p.getContent().length == 0)) {
            result.addError("Photo content must not be empty");
        }
    }
}