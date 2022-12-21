package com.cofinprobootcamp.backend.enums;

import java.util.Arrays;
import java.util.List;

/**
 * An {@code Enum} defining the different user rights that can be held be registered users.
 * It offers a robust {@code String} implementation for each type.
 * User rights are closely tied to functionality, i.e., access to certain operations on data.
 *
 * @author l-rehm
 * @version 1.1
 */
public enum UserOperationRights {
    /**
     * A constant that indicates user rights associated with creating new resources.
     */
    CREATE("create"),

    /**
     * A constant that indicates user rights associated with editing existing resources.
     */
    EDIT("edit"),

    /**
     * A constant that indicates user rights associated with viewing existing resources.
     */
    VIEW("view"),

    /**
     * A constant that indicates user rights associated with deleting existing resources.
     */
    DELETE("delete"),

    /**
     * Fallback constant in case of unsuitable input string.
     * Must always be the last defined constant!
     */
    UNDEFINED("undefined");

    /*
    Für weiterführende (allgemeine) Erläuterungen zum Aufbau dieser Enums
    siehe auch die Kommentare in "Expertises".
     */

    private static final UserOperationRights[] values;

    static {
        UserOperationRights[] tmp = UserOperationRights.values();
        values = new UserOperationRights[tmp.length - 1];
        System.arraycopy(tmp, 0, values, 0, tmp.length - 1);
    }

    private final String name;

    UserOperationRights(String name) {
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
                .map(UserOperationRights::toString)
                .toList();
    }

    /**
     * Creates a suitable UserRights type from an input {@code String}.
     *
     * @param stringName The full name as a {@code String}
     * @return An {@code UserRights} type corresponding to {@code stringName} OR {@code UserRights.UNDEFINED}
     */
    public static UserOperationRights fromString(String stringName) {
        if (stringName == null || stringName.isBlank()) {
            return UNDEFINED;
        }
        return Arrays.stream(values)
                .filter(userRights -> userRights.toString().equals(stringName))
                .findFirst()
                .orElse(UserOperationRights.UNDEFINED);
    }
}
