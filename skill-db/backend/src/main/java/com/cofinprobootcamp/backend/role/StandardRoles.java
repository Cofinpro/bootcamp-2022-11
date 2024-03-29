package com.cofinprobootcamp.backend.role;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * An {@link Enum} defining the different user roles that govern access rights on the SkillDB platform.
 * This enum basically replaces the repository (JPA persistence) and the "Role" entity at once. Actual access
 * control is handled at endpoint level currently.
 * Enum offers a robust {@link String} implementation for each type.
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
    ADMIN(
            "Administrator",
            "Mit dieser Rolle dürfen alle Profile angesehen, bearbeitet und gelöscht werden. Außerdem alle Rollen vergeben und entzogen werden."
    ) {
        @Override
        public Set<UserPrivileges> getAssociatedPrivileges() {
            return UserPrivileges.getAllValidUserPrivileges(); // ADMIN has any available privilege
        }
    },

    /**
     * User role
     * <br>
     * This role allows to create, update, delete and view profiles for the user themselves.
     */
    USER(
            "Nutzer",
            "Mit dieser Rolle dürfen alle Profile angesehen, aber nur das eigene Profil bearbeitet und gelöscht werden. Der Zugriff auf Rollen ist nicht möglich."
    ) {
        @Override
        public Set<UserPrivileges> getAssociatedPrivileges() {
            return Set.of(
                    UserPrivileges.ROLES_GET_BY_ID$SELF,
                    UserPrivileges.PROFILES_POST_NEW$SELF,
                    UserPrivileges.PROFILES_PATCH_BY_ID$SELF,
                    UserPrivileges.PROFILES_DELETE_BY_ID$SELF,
                    UserPrivileges.PROFILES_GET_BY_ID$ANY,
                    UserPrivileges.PROFILES_GET_ALL,
                    UserPrivileges.PROFILES_EXPERTISES_GET_ALL,
                    UserPrivileges.SKILLS_GET_ALL,
                    UserPrivileges.JOB_TITLES_GET_ALL,
                    UserPrivileges.USERS_POST_NEW$SELF,
                    UserPrivileges.USERS_DELETE_BY_ID$SELF,
                    UserPrivileges.USERS_GET_BY_ID$SELF,
                    UserPrivileges.USERS_BY_ID_GET_PROFILE$SELF,
                    UserPrivileges.USERS_BY_ID_GET_PROFILE_EXISTS$ANY,
                    UserPrivileges.IMAGES_GET_BY_ID,
                    UserPrivileges.IMAGES_DELETE_BY_ID$SELF
            );
        }
    },

    /**
     *
     * HR role
     * <br>
     * This role allows to create new users, provide basic profiles, view and change any existing profiles,
     * but is excluded from internal administration rights.
     */
    HR(
            "HR",
            "Mit dieser Rolle dürfen alle Profile angesehen, bearbeitet und gelöscht werden. Der Zugriff auf Rollen ist nicht möglich."
    ) {
        @Override
        public Set<UserPrivileges> getAssociatedPrivileges() {
            HashSet<UserPrivileges> privileges = new HashSet<>(USER.getAssociatedPrivileges()); // HR inherits any privilege from USER
            privileges.addAll(
                    Set.of(
                            UserPrivileges.PROFILES_POST_NEW$ANY,
                            UserPrivileges.PROFILES_PATCH_BY_ID$ANY,
                            UserPrivileges.PROFILES_DELETE_BY_ID$ANY,
                            UserPrivileges.IMAGES_DELETE_BY_ID$ANY,
                            UserPrivileges.PROFILES_EXPORT_GET_ALL,
                            UserPrivileges.PROFILES_IMPORT_POST_NEW,
                            UserPrivileges.JOB_TITLES_POST_NEW
                    )
            );
            return privileges;
        }
    },

    /**
     * Fallback constant in case of unsuitable input string.
     * Must always be the last defined constant!
     */
    UNDEFINED(
            "undefined",
            "Rolle nicht vorhanden."
    ) {
        @Override
        public Set<UserPrivileges> getAssociatedPrivileges() {
            return Set.of(); // empty set
        }
    };

    public abstract Set<UserPrivileges> getAssociatedPrivileges();

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
     * @return A {@link String} representation of display name
     */
    @Override
    public String toString() {
        return this.displayName;
    }

    public String getRoleDetailsDescription() {
        return this.roleDetailsDescription;
    }

    // Public enum static methods

    /**
     * Gets each {@link StandardRoles} constant
     * defined in the enum.
     *
     * @return An array of {@link StandardRoles} values
     */
    public static StandardRoles[] getAllDefinedValues() {
        return values;
    }

    /**
     * Creates a suitable Expertises type from an input {@link String}.
     *
     * @param identifier The short name of the desired type as a {@link String}
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

    public static StandardRoles fromDisplayName(String name) {
        if (name == null || name.isBlank()) {
            return StandardRoles.UNDEFINED;
        }
        return Arrays.stream(values)
                .filter(expertises -> expertises.displayName.equals(name))
                .findFirst()
                .orElse(StandardRoles.UNDEFINED);
    }
}
