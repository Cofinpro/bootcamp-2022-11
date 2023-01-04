package com.cofinprobootcamp.backend.user.dto;

import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;

/**
 * A DTO that is used to pass detailed information about a user, their status and their role from the application
 * to the outside. This also includes the set of user rights associated with their role.
 *
 * @param id        A unique alphanumeric {@code String} to identify this user from the outside
 * @param email     A {@code String} representation of the profile owner's email address (for identification)
 * @param locked    A {@code boolean} flag indicating whether a user account has been locked
 * @param role      A {@code RoleDetailsOutDTO} representing the user's current role
 * @param profileId An outer ID for identifying the profile associated with this user. May be {@code null}
 *                  if no profile was created for this user yet
 * @version 4.0
 */
public record UserOutDTO(String id, String email, boolean locked, RoleDetailsOutDTO role, Long profileId) {
}
