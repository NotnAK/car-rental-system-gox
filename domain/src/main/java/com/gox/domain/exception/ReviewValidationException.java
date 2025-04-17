package com.gox.domain.exception;

public class ReviewValidationException extends RuntimeException{
    public ReviewValidationException(String message) {
        super(message);
    }
}
