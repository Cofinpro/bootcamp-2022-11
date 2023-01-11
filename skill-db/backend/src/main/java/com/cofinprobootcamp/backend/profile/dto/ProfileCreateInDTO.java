package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.config.Regex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public record ProfileCreateInDTO(
        @NotBlank(message = "Email adresse darf nicht Leer sein")
        @Pattern(regexp = Regex.VALID_MAIL_ADDRESS, message = "Email adresse muss formatvorgaben entsprechen.")
        String email,
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
        @Pattern(regexp = Regex.VALID_PHONE_NUMBER, message = "Telefonnummer muss korrektes Format haben!")
        String phoneNumber,
        @NotNull(message = "Geburtsdatum muss angegeben werden!")
        @Pattern(regexp = Regex.DATE_REGEX, message = "Geburtsdatum muss im Format DD.MM.YYYY angegeben werden!")
        String birthDate,
        String profilePic) {
}
