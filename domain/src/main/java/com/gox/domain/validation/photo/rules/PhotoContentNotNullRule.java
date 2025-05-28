// PhotoContentNotNullRule.java
package com.gox.domain.validation.photo.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;

public class PhotoContentNotNullRule implements ValidationRule<PhotoValidationContext> {
    @Override
    public void validate(PhotoValidationContext ctx, ValidationResult result) {
        if (ctx.getContent() == null) {
            result.addError("Photo content must not be null");
        }
    }
}
