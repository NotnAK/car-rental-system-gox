package com.gox.domain.validation.user.rules;

import com.gox.domain.entity.user.User;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.user.UserValidationContext;

public class UserRoleNotNullRule implements ValidationRule<UserValidationContext> {
    @Override
    public void validate(UserValidationContext ctx, ValidationResult result) {
        User u = ctx.getUser();
        if (u != null && u.getRole() == null) {
            result.addError("User role must be specified");
        }
    }
}