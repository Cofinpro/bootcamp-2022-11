package com.cofinprobootcamp.backend.enums;

import java.util.Arrays;
import java.util.List;

public enum RolesEnum {
    /**
     * Administrator role
     * <br>
     * This role allows to create, update, delete and view profiles for any user.
     */
    ADMIN("Administrator") {
        @Override
        public List<UserRights> getAllAssociatedUserRights() {
            return List.of(UserRights.ANY_CREATE, UserRights.ANY_DELETE, UserRights.ANY_EDIT);
        }
    },

    /**
     * User role
     * <br>
     * This role allows to create, update, delete and view profiles for the user themselves.
     */
    USER("Nutzer") {
        @Override
        public List<UserRights> getAllAssociatedUserRights() {
            return List.of(UserRights.SELF_CREATE, UserRights.SELF_DELETE, UserRights.SELF_EDIT);
        }
    },
    /**
     * Fallback constant in case of unsuitable input string.
     * Must always be the last defined constant!
     */
    UNDEFINED("undefined") {
        @Override
        public List<UserRights> getAllAssociatedUserRights() {
            return List.of(); // Zero-element list
        }
    };

    /**
     * Gets the list of associated user rights defined for this role constant.
     * @return A {@code List} of {@code UserRights} types
     */
    public abstract List<UserRights> getAllAssociatedUserRights();

    /*
     * Zwischengespeichertes Array, da RolesEnum.values() in public Methoden benötigt wird
     * und sonst bei jedem Call neu konstruiert werden müsste.
     */
    private static final RolesEnum[] values;

    /*
     * Statische Initialisierung von values über interne Enum Methode values().
     * Das nach außen gegebene Array enthält den UNDEFINED Wert nicht.
     */
    static {
        RolesEnum[] tmp = RolesEnum.values();
        values = new RolesEnum[tmp.length - 1];
        System.arraycopy(tmp, 0, values, 0, tmp.length - 1);
    }

    // Instance field
    private final String displayName;

    // Constructor
    RolesEnum(String displayName) {
        this.displayName = displayName;
    }

    // Public enum instance methods

    /**
     * Gets the enum type's full name in a user-friendly format.
     *
     * @return A {@code String} representation of display name
     */
    @Override
    public String toString() {
        return displayName;
    }

    // Public enum static methods

    /**
     * Gets a display name {@code String} representation of each {@code RolesEnum} constant
     * defined in the enum.
     *
     * @return An array of {@code String} values
     */
    public static List<String> getAllDefinedValuesAsString() {
        return Arrays.stream(values)
                .map(RolesEnum::toString)
                .toList();
    }

    /**
     * Creates a suitable Expertises type from an input {@code String}.
     *
     * @param displayName The full name as a {@code String}
     * @return An {@code Expertises} type corresponding to {@code displayName} OR {@code Expertises.UNDEFINED}
     */
    public static RolesEnum fromDisplayName(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            return RolesEnum.UNDEFINED;
        }
        return Arrays.stream(values)
                .filter(expertises -> expertises.toString().equals(displayName))
                .findFirst()
                .orElse(RolesEnum.UNDEFINED);
    }
}
