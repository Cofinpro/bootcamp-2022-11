package com.cofinprobootcamp.backend.exceptions;

public class AuthTokenInfoOutOfSyncWithPersistenceException extends RuntimeException {
    public AuthTokenInfoOutOfSyncWithPersistenceException(Throwable cause) {
        super(cause);
    }
}
