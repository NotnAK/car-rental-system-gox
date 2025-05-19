package com.gox.domain.validation.user;

import com.gox.domain.entity.user.User;

public class UserValidationContext {
    private final User user;

    private UserValidationContext(Builder builder) {
        this.user = builder.user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public User getUser() {
        return user;
    }

    public static class Builder {
        private User user;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public UserValidationContext build() {
            return new UserValidationContext(this);
        }
    }
}