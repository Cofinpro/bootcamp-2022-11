package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.profile.Profile;

import java.util.List;

/**
 * A DTO that can be used to pass detailed information about a User to the outside.
 *
 * @param outerId          An outer ID for identification purposes
 * @param jobTitle         A {@code String} representation of the user's current job title (as of this profile)
 * @param degree           A {@code String} representation of the user's academic degree or their highest level of education
 *                         (as of this profile)
 * @param primaryExpertise A {@code String} representation of the user's primary expertise. This corresponds to the
 *                         {@code Expertises} type's full name.
 * @param referenceText    A {@code String} containing the user's references (as of this profile)
 * @param skills           A {@code List} of {@code String} objects representing the user's skills (as of this profile)
 * @param ownerEmail       A {@code String} representation of that user's email address who is the owner of the profile
 */
public record ProfileDetailsOutDTO(Long outerId, String jobTitle, String degree, String primaryExpertise,
                                   String referenceText, List<String> skills, String ownerEmail) {

    public ProfileDetailsOutDTO(Profile profile) {
        this(
                profile.getId(),
                profile.getJobTitle(),
                profile.getDegree(),
                profile.getPrimaryExpertise().toFullNameString(),
                profile.getReferenceText(),
                profile.getSkills(),
                profile.getOwner().getEmail()
        );
    }

}
