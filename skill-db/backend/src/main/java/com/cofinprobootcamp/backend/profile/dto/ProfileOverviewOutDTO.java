package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.profile.Profile;

/**
 * A DTO that can be used to pass shorthand information about a User for the overview page to the outside.
 *
 * @param outerId          An outer ID for identification purposes
 * @param firstName        A {@code String} representation of the user's first name (as of this profile)
 * @param lastName         A {@code String} representation of the user's last name (as of this profile)
 * @param jobTitle         A {@code String} representation of the user's current job title (as of this profile)
 * @param primaryExpertise A {@code String} representation of the user's primary expertise. This corresponds to the
 *                         {@code Expertises} type's full name.
 */
public record ProfileOverviewOutDTO(Long outerId, String firstName, String lastName, String jobTitle,
                                    String primaryExpertise) {
    public ProfileOverviewOutDTO(Profile profile) {
        this(
                profile.getId(),
                profile.getOwner().getFirstName(),
                profile.getOwner().getLastName(),
                profile.getJobTitle(),
                profile.getPrimaryExpertise().toFullNameString()
        );
    }
}
