package com.gox.domain.validation.review.rules;

import com.gox.domain.entity.user.User;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.review.ReviewValidationContext;

public class ReviewUserNotNullRule implements ValidationRule<ReviewValidationContext> {
    @Override
    public void validate(ReviewValidationContext ctx, ValidationResult result) {
        User user = ctx.getUser();
        if (user == null) {
            result.addError("Review must have an associated user");
        }
    }
}