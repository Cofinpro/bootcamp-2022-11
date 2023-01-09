package com.cofinprobootcamp.backend.exceptions;

public class AuthTokenInfoOutOfSyncWithPersistenceException extends RuntimeException {
    public AuthTokenInfoOutOfSyncWithPersistenceException(String message) {
        super(message);
    }
    public AuthTokenInfoOutOfSyncWithPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
