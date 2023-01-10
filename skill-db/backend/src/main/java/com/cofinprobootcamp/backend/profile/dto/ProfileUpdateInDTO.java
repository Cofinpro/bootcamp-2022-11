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
        @NotBlank(message = "Vorname darf nicht Leer sein!")
        String firstName,
        @NotBlank(message = "Nachname darf nicht Leer sein!")
        String lastName,
        @NotBlank(message = "Job Profil darf nicht leer sein!")
        String jobTitle,
        @NotBlank(message = "Abschluss darf nicht leer sein!")
        String degree,
        @NotNull(message = "Primärkompetenz darf nicht leer sein!")
        String primaryExpertise,
        @NotNull(message = "Referenzen dürfen nicht leer sein!")
        String referenceText,
        List<@NotBlank(message = "Skills dürfen nicht leer sein!") String> skills,
        @NotBlank(message = "Telefonnummer darf nicht leer sein!")
        @Pattern(regexp = Regex.VALID_PHONE_NUMBER, message = "Telefonnummer muss korrektes Format haben!") String phoneNumber,
        @NotNull(message = "Geburtsdatum muss angegeben werden!")
        @Pattern(regexp = Regex.DATE_REGEX, message = "Geburtsdatum muss im Format DD.MM.YYYY angegeben werden!")
        String birthDate,
        Long profilePicId) {
}
