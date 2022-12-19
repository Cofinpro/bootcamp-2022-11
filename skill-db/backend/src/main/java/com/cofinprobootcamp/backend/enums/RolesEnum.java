package com.cofinprobootcamp.backend.enums;

import java.util.Arrays;
import java.util.List;

public enum RolesEnum {
    ADMIN("Administrator"),
    USER("Nutzer"),
    /**
     * Fallback constant in case of unsuitable input string.
     * Must always be the last defined constant!
     */
    UNDEFINED("undefined");

    /*
     * Zwischengespeichertes Array, da Expertises.values() in public Methoden benötigt wird
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
     * Gets a full name {@code String} representation of each {@code Expertises} constant
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
    public static RolesEnum fromFullNameString(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            return RolesEnum.UNDEFINED;
        }
        return Arrays.stream(values)
                .filter(expertises -> expertises.toString().equals(displayName))
                .findFirst()
                .orElse(RolesEnum.UNDEFINED);
    }
}
