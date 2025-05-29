package com.gox.domain.validation.location.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.location.LocationValidationContext;

public class LocationCityNotNullRule implements ValidationRule<LocationValidationContext> {
    @Override
    public void validate(LocationValidationContext ctx, ValidationResult result) {
        if (ctx.getLocation().getCity() == null) {
            result.addError("City must not be null.");
        }
    }
}