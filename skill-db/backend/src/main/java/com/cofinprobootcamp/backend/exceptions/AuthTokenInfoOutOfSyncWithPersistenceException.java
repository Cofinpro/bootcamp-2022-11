package com.cofinprobootcamp.backend.exceptions;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class AuthTokenInfoOutOfSyncWithPersistenceException extends OAuth2AuthenticationException {

    public AuthTokenInfoOutOfSyncWithPersistenceException(String errorCode, String message) {
        this(errorCode, message, null);
    }

    public AuthTokenInfoOutOfSyncWithPersistenceException(String errorCode, String message, Throwable cause) {
        super(new OAuth2Error(errorCode), message, cause);
        System.out.printf("AuthTokenInfoOutOfSyncWithPersistenceException [ERROR CODE: %s]: %s%n", errorCode, message);
    }
}
