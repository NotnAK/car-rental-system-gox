package com.gox.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String identifier) {
        super("User not found with identifier: " + identifier);
    }
}
