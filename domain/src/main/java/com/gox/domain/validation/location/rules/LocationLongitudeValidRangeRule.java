package com.gox.domain.validation.location.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.location.LocationValidationContext;

public class LocationLongitudeValidRangeRule implements ValidationRule<LocationValidationContext> {
    @Override
    public void validate(LocationValidationContext ctx, ValidationResult result) {
        Double lon = ctx.getLocation() != null ? ctx.getLocation().getLongitude() : null;
        if (lon != null && (lon < -180.0 || lon > 180.0)) {
            result.addError("Longitude must be between -180 and 180.");
        }
    }
}
