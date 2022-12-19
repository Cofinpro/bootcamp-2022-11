package com.cofinprobootcamp.backend.exceptions;

import lombok.Getter;

@Getter
public class CustomErrorMessage {
    private final String message;
    private final String webRequestDescription;

    public CustomErrorMessage(String message, String webRequestDescription) {
        this.message = message;
        this.webRequestDescription = webRequestDescription;
    }
}
