package com.gox.domain.validation.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationResult {
    private final List<ValidationError> errors = new ArrayList<>();

    public void addError( String message) {
        errors.add(new ValidationError(message));
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<ValidationError> getErrors() {
        return List.copyOf(errors);
    }

    public String getCombinedMessage() {
        return errors.stream()
                .map(ValidationError::getMessage)
                .collect(Collectors.joining("; "));
    }
}
