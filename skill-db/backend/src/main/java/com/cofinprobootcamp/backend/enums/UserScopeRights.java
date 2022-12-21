package com.cofinprobootcamp.backend.enums;

import java.util.Arrays;
import java.util.List;

public enum UserScopeRights {
    NONE("none"),
    SELF("self"),
    SOME("group"),
    ANY("any"),
    UNDEFINED("undefined");

    /*
    Für weiterführende (allgemeine) Erläuterungen zum Aufbau dieser Enums
    siehe auch die Kommentare in "Expertises".
     */

    private static final UserScopeRights[] values;

    static {
        UserScopeRights[] tmp = UserScopeRights.values();
        values = new UserScopeRights[tmp.length - 1];
        System.arraycopy(tmp, 0, values, 0, tmp.length - 1);
    }

    private final String name;

    UserScopeRights(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets a {@code String} representation of each {@code UserRights} constant
     * defined in the enum.
     *
     * @return An array of {@code String} values
     */
    public static List<String> getAllDefinedValuesAsString() {
        return Arrays.stream(values)
                .map(UserScopeRights::toString)
                .toList();
    }

    /**
     * Creates a suitable UserRights type from an input {@code String}.
     *
     * @param stringName The full name as a {@code String}
     * @return An {@code UserRights} type corresponding to {@code stringName} OR {@code UserRights.UNDEFINED}
     */
    public static UserScopeRights fromString(String stringName) {
        if (stringName == null || stringName.isBlank()) {
            return UNDEFINED;
        }
        return Arrays.stream(values)
                .filter(userRights -> userRights.toString().equals(stringName))
                .findFirst()
                .orElse(UserScopeRights.UNDEFINED);
    }
}
