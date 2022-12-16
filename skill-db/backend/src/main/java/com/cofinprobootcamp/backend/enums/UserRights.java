package com.cofinprobootcamp.backend.enums;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * An {@code Enum} defining the different user rights that can be held be registered users.
 * It offers a robust {@code String} implementation for each type.
 * User rights are closely tied to functionality, i.e., access to certain operations on data.
 *
 * @author l-rehm
 * @version 1.1
 */
public enum UserRights {
    /**
     * User is allowed to create profiles for themselves.
     */
    SELF_CREATE,

    /**
     * User is allowed to edit (and therefore also view) their own profiles.
     */
    SELF_EDIT,

    /**
     * User is allowed to view their own profiles.
     */
    SELF_VIEW,

    /**
     * User is allowed to delete (and therefore also view) their own profiles.
     */
    SELF_DELETE,

    /**
     * User is allowed to create profiles for all users (and therefore also for themselves).
     */
    ANY_CREATE,

    /**
     * User is allowed to edit (and therefore also view) profiles of any user (and therefore also for themselves).
     */
    ANY_EDIT,

    /**
     * User is allowed to view profiles of any user (and therefore also for themselves).
     */
    ANY_VIEW,

    /**
     * User is allowed to delete (and therefore also view) profiles of any user (and therefore also for themselves).
     */
    ANY_DELETE,

    /**
     * Fallback constant in case of unsuitable input string.
     * Must always be the last defined constant!
     */
    UNDEFINED;

    /*
    Für weiterführende (allgemeine) Erläuterungen zum Aufbau dieser Enums
    siehe auch die Kommentare in "Expertises".
     */

    private static final UserRights[] values;

    private static final EnumSet<UserRights> selfCreateRights = EnumSet.of(UserRights.SELF_CREATE, UserRights.ANY_CREATE);
    private static final EnumSet<UserRights> selfEditRights = EnumSet.of(UserRights.SELF_EDIT, UserRights.ANY_EDIT);
    private static final EnumSet<UserRights> selfDeleteRights = EnumSet.of(UserRights.SELF_DELETE, UserRights.ANY_DELETE);
    private static final EnumSet<UserRights> selfViewRights = EnumSet.of(UserRights.SELF_VIEW, UserRights.SELF_EDIT, UserRights.SELF_DELETE, UserRights.ANY_VIEW, UserRights.ANY_EDIT, UserRights.ANY_DELETE);

    private static final EnumSet<UserRights> anyCreateRights = EnumSet.of(UserRights.ANY_CREATE);
    private static final EnumSet<UserRights> anyEditRights = EnumSet.of(UserRights.ANY_EDIT);
    private static final EnumSet<UserRights> anyDeleteRights = EnumSet.of(UserRights.ANY_DELETE);
    private static final EnumSet<UserRights> anyViewRights = EnumSet.of(UserRights.ANY_VIEW, UserRights.ANY_EDIT, UserRights.ANY_DELETE);

    static {
        UserRights[] tmp = UserRights.values();
        values = new UserRights[tmp.length - 1];
        System.arraycopy(tmp, 0, values, 0, tmp.length - 1);
    }

    /**
     * Checks whether this enum constant is associated with the right to create
     * new profiles for the user themselves.
     * @return {@code true}, if this constant enables to create own profiles
     */
    public boolean hasPermissionToCreateProfileForSelf() {
        return selfCreateRights.contains(this);
    }

    /**
     * Checks whether this enum constant is associated with the right to create
     * new profiles for any user.
     * @return {@code true}, if this constant enables to create any profiles
     */
    public boolean hasPermissionToCreateProfileForAnyone() {
        return anyCreateRights.contains(this);
    }

    /**
     * Checks whether this enum constant is associated with the right to view
     * profiles of the user themselves.
     * @return {@code true}, if this constant enables to view own profiles
     */
    public boolean hasPermissionToViewOwnProfile() {
        return selfViewRights.contains(this);
    }

    /**
     * Checks whether this enum constant is associated with the right to view
     * profiles of any users.
     * @return {@code true}, if this constant enables to view any profiles
     */
    public boolean hasPermissionToViewAnyProfile() {
        return anyViewRights.contains(this);
    }

    /**
     * Checks whether this enum constant is associated with the right to edit
     * profiles of the user themselves.
     * @return {@code true}, if this constant enables to edit own profiles
     */
    public boolean hasPermissionToEditOwnProfile() {
        return selfEditRights.contains(this);
    }

    /**
     * Checks whether this enum constant is associated with the right to edit
     * profiles of any users.
     * @return {@code true}, if this constant enables to edit any profiles
     */
    public boolean hasPermissionToEditAnyProfile() {
        return anyEditRights.contains(this);
    }

    /**
     * Checks whether this enum constant is associated with the right to delete
     * profiles of the user themselves.
     * @return {@code true}, if this constant enables to delete own profiles
     */
    public boolean hasPermissionToDeleteOwnProfile() {
        return selfDeleteRights.contains(this);
    }

    /**
     * Checks whether this enum constant is associated with the right to delete
     * profiles of any users.
     * @return {@code true}, if this constant enables to delete any profiles
     */
    public boolean hasPermissionToDeleteAnyProfile() {
        return anyDeleteRights.contains(this);
    }

    /**
     * Gets a {@code String} representation of each {@code UserRights} constant
     * defined in the enum.
     *
     * @return An array of {@code String} values
     */
    public static List<String> getAllDefinedValuesAsString() {
        return Arrays.stream(values)
                .map(UserRights::toString)
                .toList();
    }

    /**
     * Creates a suitable UserRights type from an input {@code String}.
     *
     * @param stringName The full name as a {@code String}
     * @return An {@code UserRights} type corresponding to {@code stringName} OR {@code UserRights.UNDEFINED}
     */
    public static UserRights fromString(String stringName) {
        if (stringName == null || stringName.isBlank()) {
            return UNDEFINED;
        }
        return Arrays.stream(values)
                .filter(userRights -> userRights.toString().equals(stringName))
                .findFirst()
                .orElse(UserRights.UNDEFINED);
    }
}
