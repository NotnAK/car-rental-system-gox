package com.gox.domain.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(Long id) {
        super("Location not found: " + id);
    }
}