package com.gox.domain.validation.location.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.location.LocationValidationContext;

public class LocationCityNotBlankRule implements ValidationRule<LocationValidationContext> {
    @Override
    public void validate(LocationValidationContext ctx, ValidationResult result) {
        var city = ctx.getLocation() != null ? ctx.getLocation().getCity() : null;
        if (city != null && city.isBlank()) {
            result.addError("City must not be blank.");
        }
    }
}
