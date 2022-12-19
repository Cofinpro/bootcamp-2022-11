package com.cofinprobootcamp.backend.user.dto;

import com.cofinprobootcamp.backend.enums.RolesEnum;

import javax.validation.constraints.NotBlank;

/**
 * A DTO that is used to pass information about a new user to the application. The necessary information includes
 * their email address and their future role on the platform. The status of a new user is assumed to be unlocked by
 * default and no profile exists yet.
 *
 * @param email    A {@code String} representation of the new user's email address (for identification).
 *                 Must not be blank and should match the valid email template
 * @param userRole A {@code String} representation of the new user's role (i.e., its display name)
 */
public record UserCreateInDTO(@NotBlank String email, RolesEnum userRole) {
}
