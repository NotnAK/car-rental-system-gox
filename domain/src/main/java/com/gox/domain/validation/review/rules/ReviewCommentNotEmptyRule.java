package com.gox.domain.validation.review.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.review.ReviewValidationContext;

public class ReviewCommentNotEmptyRule implements ValidationRule<ReviewValidationContext> {
    @Override
    public void validate(ReviewValidationContext ctx, ValidationResult result) {
        String c = ctx.getComment();
        if (c == null || c.isBlank()) {
            result.addError("Review comment must not be blank");
        }
    }
}