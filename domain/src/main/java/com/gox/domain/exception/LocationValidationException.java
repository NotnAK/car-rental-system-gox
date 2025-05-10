package com.gox.domain.exception;

public class LocationValidationException extends RuntimeException {
    public LocationValidationException(String message) {
        super(message);
    }
}