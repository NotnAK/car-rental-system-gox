package com.gox.domain.validation.user.rules;

import com.gox.domain.entity.user.User;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.user.UserValidationContext;

public class UserNameFormatRule implements ValidationRule<UserValidationContext> {
    @Override
    public void validate(UserValidationContext ctx, ValidationResult result) {
        User u = ctx.getUser();
        String name = u != null ? u.getName() : null;
        if (name == null || name.isBlank()) {
            return;
        }
        String[] parts = name.trim().split("\\s+");
        if (parts.length < 2) {
            result.addError("User name must contain first name and last name separated by space");
        } else {
            for (String p : parts) {
                if (p.isBlank()) {
                    result.addError("User name parts must not be blank");
                    break;
                }
            }
        }
    }
}
