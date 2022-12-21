package com.cofinprobootcamp.backend.user.dto;

import com.cofinprobootcamp.backend.config.Regex;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * A DTO that is used to pass information about a new user to the application. The necessary information includes
 * their email address and their future role on the platform. The status of a new user is assumed to be unlocked by
 * default and no profile exists yet.
 *
 * @param email    A {@code String} representation of the new user's email address (for identification).
 *                 Must not be blank and should match the valid email template
 * @param userRole A {@code String} representation of the new user's role (i.e., its short name)
 * @param password A {@code String} representing the user's password (it must have a minimum length of 8 characters)
 */
public record UserCreateInDTO(@NotBlank @Pattern(regexp = Regex.VALID_MAIL_ADDRESS) String email,
                              @NotBlank @Length(min = Regex.MINIMUM_PASSWORD_LENGTH) String password, String userRole) {
}
