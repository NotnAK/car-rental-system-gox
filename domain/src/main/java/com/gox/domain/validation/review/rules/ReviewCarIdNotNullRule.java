package com.gox.domain.validation.review.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.review.ReviewValidationContext;

public class ReviewCarIdNotNullRule implements ValidationRule<ReviewValidationContext> {
    @Override
    public void validate(ReviewValidationContext ctx, ValidationResult result) {
        Long id = ctx.getCarId();
        if (id == null || id <= 0) {
            result.addError("Car ID for review must be provided and positive");
        }
    }
}