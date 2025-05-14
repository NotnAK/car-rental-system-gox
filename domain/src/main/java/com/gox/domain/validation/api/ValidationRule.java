package com.gox.domain.validation.api;

public interface ValidationRule<C> {
    void validate(C context, ValidationResult result);
}