package com.cofinprobootcamp.backend.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    private static final String standardMessage = "Ein Nutzer mit der angegebenen E-Mail existiert bereits!";

    public UserAlreadyExistsException() {
        super(standardMessage);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
