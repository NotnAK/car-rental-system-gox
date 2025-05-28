// PhotoNameNotEmptyRule.java
package com.gox.domain.validation.photo.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.photo.PhotoValidationContext;

public class PhotoNameNotEmptyRule implements ValidationRule<PhotoValidationContext> {
    @Override
    public void validate(PhotoValidationContext ctx, ValidationResult result) {
        String name = ctx.getName();
        if (name != null && name.isBlank()) {
            result.addError("Photo name must not be empty");
        }
    }
}
