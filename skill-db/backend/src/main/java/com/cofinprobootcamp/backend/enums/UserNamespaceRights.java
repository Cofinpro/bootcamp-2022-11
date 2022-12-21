package com.cofinprobootcamp.backend.enums;

import com.cofinprobootcamp.backend.config.Regex;
import com.cofinprobootcamp.backend.exceptions.EndPointNotFoundException;

import java.util.Arrays;
import java.util.List;

public enum UserNamespaceRights {
    PROFILES(Regex.BASE_URL + "/profiles"),
    USERS(Regex.BASE_URL + "/users"),
    ROLES(Regex.BASE_URL + "/roles"),
    SKILLS(Regex.BASE_URL + "/skills"),
    JOB_TITLES(Regex.BASE_URL + "/job-titles");

    private static final UserNamespaceRights[] values = UserNamespaceRights.values();

    private final String namespace;

    UserNamespaceRights(String namespace) {
        this.namespace = namespace;
    }

    public String toNamespaceString() {
        return namespace;
    }

    /**
     * Gets a {@code String} representation of each {@code UserRights} constant
     * defined in the enum.
     *
     * @return An array of {@code String} values
     */
    public static List<String> getAllDefinedValuesAsString() {
        return Arrays.stream(values)
                .map(UserNamespaceRights::toNamespaceString)
                .toList();
    }

    /**
     * Creates a suitable UserRights type from an input {@code String}.
     *
     * @param stringName The full name as a {@code String}
     * @return An {@code UserRights} type corresponding to {@code stringName} OR {@code UserRights.UNDEFINED}
     */
    public static UserNamespaceRights fromString(String stringName) {
        return Arrays.stream(values)
                .filter(userRights -> userRights.toNamespaceString().equals(stringName))
                .findFirst()
                .orElseThrow(EndPointNotFoundException::new);
    }
}
