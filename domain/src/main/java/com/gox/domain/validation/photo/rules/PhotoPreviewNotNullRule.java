// PhotoIsPreviewNotNullRule.java
package com.gox.domain.validation.photo.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;

public class PhotoPreviewNotNullRule implements ValidationRule<PhotoValidationContext> {
    @Override
    public void validate(PhotoValidationContext ctx, ValidationResult result) {
        if (ctx.getPreview() == null) {
            result.addError("isPreview flag must not be null");
        }
    }
}
