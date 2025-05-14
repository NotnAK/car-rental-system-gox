package com.gox.domain.validation.user;

import com.gox.domain.entity.user.User;

public class UserValidationContext {
    private final User user;
    public UserValidationContext(User user) { this.user = user; }
    public User getUser() { return user; }
}