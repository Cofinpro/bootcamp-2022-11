package com.cofinprobootcamp.backend.enums;

import java.util.Arrays;
import java.util.List;

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

    private final String fullName;

    Expertises(String fullName) {
        this.fullName = fullName;
    }

    public String toFullNameString() {
        return fullName;
    }

    /**
     * Gets a full name {@link String} representation of each {@link Expertises} constant
     * defined in the enum.
     *
     * @return An array of {@link String} values
     */
    public static List<String> getAllDefinedValuesAsString() {
        return Arrays.stream(values)
                .map(Expertises::toFullNameString)
                .toList();
    }

    /**
     * Creates a suitable Expertises type from an input {@link String}.
     *
     * @param fullName The full name as a {@link String}
     * @return An {@link Expertises} type corresponding to {@code fullName} OR {@code Expertises.UNDEFINED}
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
