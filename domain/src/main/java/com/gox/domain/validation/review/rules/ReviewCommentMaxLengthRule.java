package com.gox.domain.validation.review.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.review.ReviewValidationContext;

public class ReviewCommentMaxLengthRule implements ValidationRule<ReviewValidationContext> {

    private static final int MAX_LENGTH = 150;

    @Override
    public void validate(ReviewValidationContext ctx, ValidationResult result) {
        String comment = ctx.getComment();
        if (comment != null && comment.length() > MAX_LENGTH) {
            result.addError("Comment must be at most " + MAX_LENGTH + " characters");
        }
    }
}