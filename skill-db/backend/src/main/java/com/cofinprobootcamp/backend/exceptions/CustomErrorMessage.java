package com.cofinprobootcamp.backend.exceptions;

import lombok.Getter;

@Getter
public class CustomErrorMessage {
    private final String message;
    private final String webRequestDescription;
    private final ErrorCause cause;

    private record ErrorCause(String causeExceptionName, String causeMessage) {
        ErrorCause(Throwable cause) {
            this(
                    cause.getClass().getSimpleName(),
                    cause.getMessage()
            );
        }
    }

    public CustomErrorMessage(String message, String webRequestDescription) {
        this.message = message;
        this.webRequestDescription = webRequestDescription;
        this.cause = null;
    }

    public CustomErrorMessage(String message, String webRequestDescription, Throwable cause) {
        this.message = message;
        this.webRequestDescription = webRequestDescription;
        this.cause = cause != null ? new ErrorCause(cause) : null;
    }
}
