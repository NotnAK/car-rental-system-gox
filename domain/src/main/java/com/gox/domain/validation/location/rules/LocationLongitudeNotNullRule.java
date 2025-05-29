package com.gox.domain.validation.location.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.location.LocationValidationContext;

public class LocationLongitudeNotNullRule implements ValidationRule<LocationValidationContext> {
    @Override
    public void validate(LocationValidationContext ctx, ValidationResult result) {
        if (ctx.getLocation() != null && ctx.getLocation().getLongitude() == null) {
            result.addError("Longitude must not be null.");
        }
    }
}
