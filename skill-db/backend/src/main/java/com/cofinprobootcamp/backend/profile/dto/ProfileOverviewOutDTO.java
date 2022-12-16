package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.profile.Profile;

public record ProfileOverviewOutDTO(
        Long id,
        String name,
        String jobTitle,
        String primaryExpertise
) {
    public ProfileOverviewOutDTO(Profile profile){
        this(
                profile.getId(),
                profile.getFirstName() + " " + profile.getLastName(),
                profile.getJobTitle(),
                profile.getPrimaryExpertise()
                        .toFullNameString()
        );
    }
}
