package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.config.Regex;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

/**
 * A DTO that is used to pass detailed information about an update to an existing profile to the application.
 * The application searches for a corresponding {@code Profile} entity in the database and, if successful, updates
 * its contents and persists the new entity.
 *
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
 */
public record ProfileUpdateInDTO(
        @NotBlank(message = "Field  first name can not be left blank!")
        String firstName,
        @NotBlank(message = "Field last name can not be left blank!")
        String lastName,
        @NotBlank(message = "Field job title can not be left blank!")
        String jobTitle,
        @NotBlank(message = "Field degree can not be left blank!")
        String degree,
        @NotNull(message = "Field Expertise can not be left blank!")
        String primaryExpertise,
        @NotNull(message = "Reference text can not be left blank!")
        String referenceText,
        @NotEmpty(message = "Skill List can not be left blank")
        List<@NotBlank(message = "Entry in skill list can not be blank") String> skills,
        @NotBlank(message = "phone number can not be left blank")
        @Pattern(regexp = Regex.VALID_PHONE_NUMBER, message = "phone number has to be a valid format") String phoneNumber,
        @NotNull(message = "Date can not be null") @JsonFormat(pattern = Regex.DATE_FORMAT) LocalDate birthDate) {
}
