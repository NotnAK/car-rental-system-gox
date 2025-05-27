package com.gox.domain.validation.photo.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.photo.PhotoValidationContext;
import com.gox.domain.validation.api.ValidationRule;
public class PhotoNameNotEmptyRule implements ValidationRule<PhotoValidationContext> {
    @Override
    public void validate(PhotoValidationContext ctx, ValidationResult result) {
        var p = ctx.getPhoto();
        if (p != null && (p.getName() == null || p.getName().isBlank())) {
            result.addError("Photo name must not be empty");
        }
    }
}