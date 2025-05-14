package com.gox.domain.validation.review.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.review.ReviewValidationContext;

public class ReviewRatingRangeRule implements ValidationRule<ReviewValidationContext> {
    @Override
    public void validate(ReviewValidationContext ctx, ValidationResult result) {
        Integer rating = ctx.getRating();
        if (rating == null || rating < 1 || rating > 5) {
            result.addError("Rating must be between 1 and 5");
        }
    }
}