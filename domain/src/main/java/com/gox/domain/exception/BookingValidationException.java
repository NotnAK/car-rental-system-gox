package com.gox.domain.exception;

public class BookingValidationException extends RuntimeException{
    public BookingValidationException(String message) {
        super(message);
    }
}
