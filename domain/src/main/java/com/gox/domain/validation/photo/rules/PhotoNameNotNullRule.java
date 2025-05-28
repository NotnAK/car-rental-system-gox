// PhotoNameNotNullRule.java
package com.gox.domain.validation.photo.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;

public class PhotoNameNotNullRule implements ValidationRule<PhotoValidationContext> {
    @Override
    public void validate(PhotoValidationContext ctx, ValidationResult result) {
        if (ctx.getName() == null) {
            result.addError("Photo name must not be null");
        }
    }
}
