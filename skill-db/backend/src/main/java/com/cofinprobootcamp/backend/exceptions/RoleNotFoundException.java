package com.cofinprobootcamp.backend.exceptions;

public class RoleNotFoundException extends RuntimeException {
    private final String givenRoleName;
    private static final String template = "[Fehler bei Rolle: %s] Es existiert keine Rolle mit diesem Namen!";

    public RoleNotFoundException(String givenRoleName) {
        this.givenRoleName = givenRoleName;
    }

    public String getRoleErrorMessage() {
        return String.format(template, givenRoleName);
    }
}
