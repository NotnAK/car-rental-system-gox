package com.gox.domain.validation.location.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.location.LocationValidationContext;

public class LocationStreetNotBlankRule implements ValidationRule<LocationValidationContext> {
    @Override
    public void validate(LocationValidationContext ctx, ValidationResult result) {
        var street = ctx.getLocation() != null ? ctx.getLocation().getStreet() : null;
        if (street != null && street.isBlank()) {
            result.addError("Street must not be blank.");
        }
    }
}
