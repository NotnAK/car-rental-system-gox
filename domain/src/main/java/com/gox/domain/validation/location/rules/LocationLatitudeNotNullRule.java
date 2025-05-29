package com.gox.domain.validation.location.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.location.LocationValidationContext;

public class LocationLatitudeNotNullRule implements ValidationRule<LocationValidationContext> {
    @Override
    public void validate(LocationValidationContext ctx, ValidationResult result) {
        if (ctx.getLocation().getLatitude() == null) {
            result.addError("Latitude must not be null.");
        }
    }
}
