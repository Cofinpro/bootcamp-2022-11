package com.cofinprobootcamp.backend.user.dto;

import com.cofinprobootcamp.backend.role.RoleDirector;
import com.cofinprobootcamp.backend.user.User;

import java.util.Map;

/**
 * A DTO that is used to pass detailed information about a user, their status and their role from the application
 * to the outside. This also includes the set of user rights associated with their role.
 *
 * @param email          A {@code String} representation of the profile owner's email address (for identification)
 * @param locked         A {@code boolean} flag indicating whether a user account has been locked
 * @param role           A {@code String} representation of the user's current role (i.e., its display name)
 * @param userRights     A {@code List} of {@code String} values representing the role's associated rights
 *                       (These are {@code String} conversions of {@code UserRights} enum constants)
 * @param profileOuterId An outer ID for identifying the profile associated with this user. May be {@code null}
 *                       if no profile was created for this user yet
 * @version 2.0
 */
public record UserOutDTO(String email, boolean locked, String role, Map<String, Map<String, String>> userRights, Long profileOuterId) {
    public UserOutDTO(User user) {
        this(
                user.getUsername(),
                user.isLocked(),
                user.getRole() != null ? user.getRole().getDescriptiveName() : null,
                user.getRole() != null ? RoleDirector.roleUserRightsToDTOMap(user.getRole()) : null,
                user.getProfile() != null ? user.getProfile().getId() : null
        );
    }
}
