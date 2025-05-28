package com.gox.domain.validation.user.rules;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.user.UserValidationContext;
import com.gox.domain.entity.user.LoyaltyLevel;

public class LoyaltyLevelFormatRule implements ValidationRule<UserValidationContext> {

    @Override
    public void validate(UserValidationContext ctx, ValidationResult result) {
        String lvl = ctx.getLoyaltyLevel();  // добавьте геттер в контекст
        if (lvl == null || lvl.isBlank()) {
            result.addError("loyaltyLevel is required");
            return;
        }
        try {
            LoyaltyLevel.valueOf(lvl);
        } catch (IllegalArgumentException e) {
            result.addError("Invalid loyaltyLevel: " + lvl +
                    ". Allowed values: " + String.join(", ",
                    java.util.Arrays.stream(LoyaltyLevel.values())
                            .map(Enum::name)
                            .toArray(String[]::new)
            ));
        }
    }
}
