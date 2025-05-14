package com.gox.domain.validation.review.rules;

import com.gox.domain.exception.ReviewValidationException;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.review.ReviewValidationContext;

public class ReviewIdNotNullRule  implements ValidationRule<ReviewValidationContext> {
    @Override
    public void validate(ReviewValidationContext ctx, ValidationResult result) {
        Long id = ctx.getReviewId();
        if (id == null || id <= 0) {
            result.addError("Review ID must be provided and positive");
        }
    }
}