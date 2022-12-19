package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.profile.Profile;

/**
 * A DTO that can be used to pass shorthand information about a User for the overview page to the outside.
 *
 * @param outerId          An outer ID for identification purposes
 * @param name             A {@code String} representation of the user's full name (as of this profile)
 * @param jobTitle         A {@code String} representation of the user's current job title (as of this profile)
 * @param primaryExpertise A {@code String} representation of the user's primary expertise. This corresponds to the
 *                         {@code Expertises} type's full name.
 */
public record ProfileOverviewOutDTO(Long outerId, String name, String jobTitle, String primaryExpertise) {
    public ProfileOverviewOutDTO(Profile profile) {
        this(
                profile.getId(),
                profile.getFirstName() + " " + profile.getLastName(),
                profile.getJobTitle(),
                profile.getPrimaryExpertise().toFullNameString()
        );
    }
}
