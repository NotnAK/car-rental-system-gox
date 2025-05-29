package com.gox.domain.validation.location.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.location.LocationValidationContext;

public class LocationLatitudeValidRangeRule implements ValidationRule<LocationValidationContext> {
    @Override
    public void validate(LocationValidationContext ctx, ValidationResult result) {
        Double lat = ctx.getLocation() != null ? ctx.getLocation().getLatitude() : null;
        if (lat != null && (lat < -90.0 || lat > 90.0)) {
            result.addError("Latitude must be between -90 and 90.");
        }
    }
}
