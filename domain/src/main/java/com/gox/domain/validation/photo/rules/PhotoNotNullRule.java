package com.gox.domain.validation.photo.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;

public class PhotoNotNullRule implements ValidationRule<PhotoValidationContext> {
    @Override
    public void validate(PhotoValidationContext ctx, ValidationResult result) {
        if (ctx.getPhoto() == null) {
            result.addError("Photo must not be null");
        }
    }
}