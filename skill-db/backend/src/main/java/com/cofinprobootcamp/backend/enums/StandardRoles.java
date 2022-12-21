package com.cofinprobootcamp.backend.enums;

import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.role.RoleDirector;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum StandardRoles {
    /**
     * Administrator role
     * <br>
     * This role allows to create, update, delete and view profiles for any user.
     */
    ADMIN("Administrator") {
        @Override
        protected Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> overrideSpecificValues(
                Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> userRights) {
            for (var nameSpace : UserNamespaceRights.values()) {
                userRights.get(nameSpace).put(UserOperationRights.CREATE, UserScopeRights.ANY);
                userRights.get(nameSpace).put(UserOperationRights.VIEW, UserScopeRights.ANY);
                userRights.get(nameSpace).put(UserOperationRights.EDIT, UserScopeRights.ANY);
                userRights.get(nameSpace).put(UserOperationRights.DELETE, UserScopeRights.ANY);
            }
            return userRights;
        }
    },

    /**
     * User role
     * <br>
     * This role allows to create, update, delete and view profiles for the user themselves.
     */
    USER("Nutzer") {
        @Override
        protected Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> overrideSpecificValues(
                Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> userRights) {
            userRights.get(UserNamespaceRights.PROFILES).put(UserOperationRights.CREATE, UserScopeRights.SELF);
            userRights.get(UserNamespaceRights.PROFILES).put(UserOperationRights.VIEW, UserScopeRights.SELF);
            userRights.get(UserNamespaceRights.PROFILES).put(UserOperationRights.EDIT, UserScopeRights.SELF);
            userRights.get(UserNamespaceRights.PROFILES).put(UserOperationRights.DELETE, UserScopeRights.SELF);
            userRights.get(UserNamespaceRights.SKILLS).put(UserOperationRights.VIEW, UserScopeRights.ANY);
            userRights.get(UserNamespaceRights.JOB_TITLES).put(UserOperationRights.VIEW, UserScopeRights.ANY);
            userRights.get(UserNamespaceRights.ROLES).put(UserOperationRights.VIEW, UserScopeRights.ANY);
            return userRights;
        }
    },
    /**
     * Fallback constant in case of unsuitable input string.
     * Must always be the last defined constant!
     */
    UNDEFINED("undefined") {
        @Override
        protected Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> overrideSpecificValues(
                Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> userRights) {
            return userRights;
        }
    };

    /**
     * Creates a new {@code Role} entity depending on the implementing enum constant.
     *
     * @return A {@code Role} object with set name and {@code UserRights}
     */
    public Role createNewRoleEntity() {
        return RoleDirector.roleFromSpecification(this.name(), this.toString(), this.getAllAssociatedUserRights());
    }

    /**
     * Gets the list of associated user rights defined for this role constant.
     *
     * @return A {@code List} of {@code UserRights} types
     */
    public Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> getAllAssociatedUserRights() {
        Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> userRights = new EnumMap<>(UserNamespaceRights.class);
        for (var nameSpace : UserNamespaceRights.values()) {
            userRights.put(nameSpace, RoleDirector.generateDefaults());
        }
        return overrideSpecificValues(userRights);
    }

    protected abstract Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> overrideSpecificValues(
            Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> userRights);

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

    // Instance field
    private final String displayName;

    // Constructor
    StandardRoles(String displayName) {
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
        return Arrays.stream(values).map(StandardRoles::name).toList();
    }

    /**
     * Creates a suitable Expertises type from an input {@code String}.
     *
     * @param shortName The full name as a {@code String}
     * @return An {@code Expertises} type corresponding to {@code shortName} OR {@code Expertises.UNDEFINED}
     */
    public static StandardRoles fromShortName(String shortName) {
        if (shortName == null || shortName.isBlank()) {
            return StandardRoles.UNDEFINED;
        }
        return Arrays.stream(values).filter(expertises -> expertises.name().equals(shortName)).findFirst().orElse(StandardRoles.UNDEFINED);
    }
}
