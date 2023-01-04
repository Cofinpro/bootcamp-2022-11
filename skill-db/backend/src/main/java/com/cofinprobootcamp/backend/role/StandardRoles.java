package com.cofinprobootcamp.backend.role;

import java.util.Arrays;
import java.util.List;

/**
 * An {@code Enum} defining the different user roles that govern access rights on the SkillDB platform.
 * This enum basically replaces the repository (JPA persistence) and the "Role" entity at once. Actual access
 * control is handled at endpoint level currently.
 * Enum offers a robust {@code String} implementation for each type.
 *
 * @author l-rehm
 * @version 2.0
 */
public enum StandardRoles {
    /**
     * Administrator role
     * <br>
     * This role allows to create, update, delete and view profiles for any user.
     */
    ADMIN("Administrator", "Mit dieser Rolle dürfen alle Profile angesehen, bearbeitet und gelöscht werden. Außerdem alle Rollen vergeben und entzogen werden."), //TODO: Add description text

    /**
     * User role
     * <br>
     * This role allows to create, update, delete and view profiles for the user themselves.
     */
    USER("Nutzer", "Mit dieser Rolle dürfen alle Profile angesehen, aber nur das eigene Profil bearbeitet und gelöscht werden. Der Zugriff auf Rollen ist nicht möglich."), //TODO: Add description text

    /**
     *
     * HR role
     * <br>
     * This role allows to create new users, provide basic profiles, view and change any existing profiles,
     * but is excluded from internal administration rights.
     */
    HR("Personalabteilung/Human Resources", "Mit dieser Rolle dürfen alle Profile angesehen, bearbeitet und gelöscht werden. Der Zugriff auf Rollen ist nicht möglich."), //TODO: Add description text

    /**
     * Fallback constant in case of unsuitable input string.
     * Must always be the last defined constant!
     */
    UNDEFINED("undefined", "Rolle nicht vorhanden.");

    /*
     * Zwischengespeichertes Array, da RolesEnum.values() in public Methoden benötigt wird
     * und sonst bei jedem Call neu konstruiert werden müsste.
     */
    private static final StandardRoles[] values;

    /*
     * Statische Initialisierung von values über interne Enum Methode values().
     * Das nach außen gegebene Array enthält den UNDEFINED Wert nicht.
     */
    static {
        StandardRoles[] tmp = StandardRoles.values();
        values = new StandardRoles[tmp.length - 1];
        System.arraycopy(tmp, 0, values, 0, tmp.length - 1);
    }

    // Instance fields
    private final String displayName;
    private final String roleDetailsDescription;

    // Constructor
    StandardRoles(String displayName, String roleDetailsDescription) {
        this.displayName = displayName;
        this.roleDetailsDescription = roleDetailsDescription;
    }

    // Public enum instance methods

    /**
     * Gets the enum type's full name in a user-friendly format.
     *
     * @return A {@code String} representation of display name
     */
    @Override
    public String toString() {
        return this.displayName;
    }

    /**
     * Gets the enum type's detailed description.
     *
     * @return A {@code String} representation of roleDetailsDescription
     */
    public String getRoleDetailsDescription() {
        return this.roleDetailsDescription;
    }

    // Public enum static methods

    /**
     * Gets a short name representation of each {@code StandardRoles} constant
     * defined in the enum.
     *
     * @return A {@code List} of {@code String} values
     */
    public static List<String> getAllDefinedValuesAsShortName() {
        return Arrays.stream(values).map(StandardRoles::name).toList();
    }

    /**
     * Gets a display name representation of each {@code RolesEnum} constant
     * defined in the enum.
     *
     * @return A {@code List} of {@code String} values
     */
    public static List<String> getAllDefinedValuesAsDisplayName() {
        return Arrays.stream(values).map(StandardRoles::toString).toList();
    }

    /**
     * Creates a suitable Expertises type from an input {@code String}.
     *
     * @param identifier The short name of the desired type as a {@code String}
     * @return An {@code Expertises} type corresponding to {@code identifier} OR {@code Expertises.UNDEFINED}
     */
    public static StandardRoles fromIdentifier(String identifier) {
        if (identifier == null || identifier.isBlank()) {
            return StandardRoles.UNDEFINED;
        }
        return Arrays.stream(values)
                .filter(expertises -> expertises.name().equals(identifier))
                .findFirst()
                .orElse(StandardRoles.UNDEFINED);
    }
}
