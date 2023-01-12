package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.skills.Skill;

import java.util.List;

/**
 * A DTO that can be used to pass detailed information about a User to the outside.
 *
 * @param id               An outer ID for identification purposes (is a unique alphanumeric {@code String})
 * @param email            A {@code String} representation of the user's email address (must be unique)
 * @param phoneNumber      A {@code String} representation of the user's phone number
 * @param jobTitle         A {@code String} representation of the user's current job title (as of this profile)
 * @param degree           A {@code String} representation of the user's academic degree or their highest level of education
 *                         (as of this profile)
 * @param primaryExpertise A {@code String} representation of the user's primary expertise. This corresponds to the
 *                         {@code Expertises} type's full name.
 * @param referenceText    A {@code String} containing the user's references (as of this profile)
 * @param skills           A {@code List} of {@code String} objects representing the user's skills (as of this profile)
 * @param firstName        A {@code String} representation of that user's first name who is the owner of the profile
 * @param lastName         A {@code String} representation of that user's last name who is the owner of the profile
 * @param birthDate        A {@code String} representation of that user's birthdate who is the owner of the profile
 *                         (The format is specified as "yyyy-MM-dd")
 * @param age              The user's age as calculated per {@code birthDate} ({@code Integer} value)
 * @param ownerId          The outer ID of the user who owns this profile
 * @param profilePicId     A {@code Long} representing an associated profile picture
 */
public record ProfileDetailsOutDTO(String id,
                                   String email,
                                   String phoneNumber,
                                   String jobTitle,
                                   String degree,
                                   String primaryExpertise,
                                   String referenceText,
                                   List<String> skills,
                                   String firstName,
                                   String lastName,
                                   String birthDate,
                                   Integer age,
                                   String ownerId,
                                   Long profilePicId) {

    public ProfileDetailsOutDTO(Profile profile) {
        this(
                profile.getOuterId(),
                profile.getOwner().getUsername(),
                profile.getPhoneNumber(),
                profile.getJobTitle().getName(),
                profile.getDegree(),
                profile.getPrimaryExpertise().toFullNameString(),
                profile.getReferenceText(),
                profile.getSkillSet().stream()
                        .map(Skill::getName)
                        .toList(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getBirthDate().toString(), // ISO Format String as specified in default setting (could also be .format(new DateTimeFormatterBuilder().appendLiteral(Regex.DATE_FORMAT).toFormatter()) )
                profile.getAge(),
                profile.getOwner().getOuterId(),
                profile.getProfilePic()==null? null : profile.getProfilePic().getId());
    }

}
