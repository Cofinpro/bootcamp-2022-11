package com.cofinprobootcamp.backend.exceptions;

public class JobTitleAlreadyExistsException extends RuntimeException {
    public JobTitleAlreadyExistsException(String message) {
        super(message);
    }
}
