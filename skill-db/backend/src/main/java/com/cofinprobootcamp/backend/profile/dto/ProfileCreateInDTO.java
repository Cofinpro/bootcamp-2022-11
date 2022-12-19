package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.config.Regex;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

/**
 * A DTO that is used to pass detailed information about a new profile to the application.
 * The application creates a new {@code Profile} entity from this that can be persisted to the database.
 *
 * @param email            A {@code String} representation of the profile owner's email address (for identification).
 *                         Must not be blank and should match the valid email template
 * @param firstName        A {@code String} representation of the profile owner's first name (as of this profile).
 *                         Must not be blank
 * @param lastName         A {@code String} representation of the profile owner's last name (as of this profile).
 *                         Must not be blank
 * @param jobTitle         A {@code String} representation of the profile owner's current job title (as of this profile).
 *                         Must not be blank
 * @param degree           A {@code String} representation of the profile owner's current degree (as of this profile).
 *                         Must not be blank
 * @param primaryExpertise A {@code String} representation of the profile owner's primary expertise. This corresponds to
 *                         the {@code Expertises} type's full name. Cannot be a {@code null} value
 * @param referenceText    A {@code String} containing the profile owner's references (as of this profile). Can be blank
 * @param skills           A {@code List} of {@code String} objects representing the profile owner's skills
 *                         (as of this profile). Must be a non-empty list of non-blank {@code String}s
 * @param phoneNumber      A {@code String} representing the profile owner's business phone number (as of this profile).
 *                         Must not be blank and must match the standard phone no. template
 * @param birthDate        A {@code String} representation of that user's birthdate who is the owner of the profile
 *                         (The format is specified as "yyyy-MM-dd"). Cannot be {@code null}
 */
public record ProfileCreateInDTO(@NotBlank @Pattern(regexp = Regex.VALID_MAIL_ADDRESS) String email,
                                 @NotBlank String firstName, @NotBlank String lastName, @NotBlank String jobTitle,
                                 @NotBlank String degree, @NotNull String primaryExpertise, String referenceText,
                                 @NotEmpty List<@NotBlank String> skills,
                                 @NotBlank @Pattern(regexp = Regex.VALID_PHONE_NUMBER) String phoneNumber,
                                 @NotNull @JsonFormat(pattern = Regex.DATE_FORMAT) LocalDate birthDate) {
}
