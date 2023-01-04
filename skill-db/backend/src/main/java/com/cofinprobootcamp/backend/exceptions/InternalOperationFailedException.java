package com.cofinprobootcamp.backend.exceptions;

public class InternalOperationFailedException extends RuntimeException{
    public InternalOperationFailedException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
