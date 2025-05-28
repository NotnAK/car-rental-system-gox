package com.gox.domain.validation.user;

import com.gox.domain.entity.user.User;

public class UserValidationContext {
    private final User user;
    private final String loyaltyLevel;
    private UserValidationContext(Builder builder) {
        this.user = builder.user;
        this.loyaltyLevel = builder.loyaltyLevel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public User getUser() {
        return user;
    }

    public String getLoyaltyLevel() {
        return loyaltyLevel;
    }

    public static class Builder {
        private User user;
        private String loyaltyLevel;
        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder loyaltyLevel(String loyaltyLevel) {
            this.loyaltyLevel = loyaltyLevel;
            return this;
        }

        public UserValidationContext build() {
            return new UserValidationContext(this);
        }
    }
}