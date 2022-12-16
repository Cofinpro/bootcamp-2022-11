package com.cofinprobootcamp.backend.enums;

import java.util.Arrays;
import java.util.List;

/**
 * An {@code Enum} defining the different types of expertise distinguished by Cofinpro.
 * It offers a robust {@code String} implementation for each type.
 *
 * @author l-rehm
 * @version 1.1
 */
public enum Expertises {
    /**
     * Primärkompetenz Management
     */
    MGMT("Management"),

    /**
     * Primärkompetenz Fach
     */
    SPEC("Fach"),

    /**
     * Primärkompetenz Technologie
     */
    TECH("Technologie"),

    /**
     * Fallback constant in case of unsuitable input string.
     * Must always be the last defined constant!
     */
    UNDEFINED("undefined");

    /*
     * Zwischengespeichertes Array, da Expertises.values() in public Methoden benötigt wird
     * und sonst bei jedem Call neu konstruiert werden müsste.
     */
    private static final Expertises[] values;

    /*
     * Statische Initialisierung von values über interne Enum Methode values().
     * Das nach außen gegebene Array enthält den UNDEFINED Wert nicht.
     */
    static {
        Expertises[] tmp = Expertises.values();
        values = new Expertises[tmp.length - 1];
        System.arraycopy(tmp, 0, values, 0, tmp.length - 1);
    }

    // Instance field
    private final String fullName;

    // Constructor
    Expertises(String fullName) {
        this.fullName = fullName;
    }

    // Public enum instance methods

    /*
     * toString() ist automatisch implementiert und liefert den Namen
     * der Enum-Konstante als String.
     */

    /**
     * Gets the enum type's full name.
     *
     * @return A {@code String} representation of full name
     */
    public String toFullNameString() {
        return fullName;
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
                .map(Expertises::toFullNameString)
                .toList();
    }

    /**
     * Creates a suitable Expertises type from an input {@code String}.
     *
     * @param fullName The full name as a {@code String}
     * @return An {@code Expertises} type corresponding to {@code fullName} OR {@code Expertises.UNDEFINED}
     */
    public static Expertises fromFullNameString(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return Expertises.UNDEFINED;
        }
        return Arrays.stream(values)
                .filter(expertises -> expertises.toFullNameString().equals(fullName))
                .findFirst()
                .orElse(Expertises.UNDEFINED);
    }
}
