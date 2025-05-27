package com.gox.domain.validation.user.rules;

import com.gox.domain.entity.user.User;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.user.UserValidationContext;

public class UserPhoneNotEmptyRule implements ValidationRule<UserValidationContext> {
    @Override
    public void validate(UserValidationContext ctx, ValidationResult result) {
        User u = ctx.getUser();
        if (u != null) {
            String phone = u.getPhone();
            if (phone == null || phone.isBlank()) {
                result.addError("User phone must not be empty");
            }
        }
    }
}