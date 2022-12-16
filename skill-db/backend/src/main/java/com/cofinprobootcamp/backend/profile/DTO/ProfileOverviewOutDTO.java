package com.cofinprobootcamp.backend.profile.DTO;

import com.cofinprobootcamp.backend.profile.Profile;

public record ProfileOverviewOutDTO(
        Long id,
        String firstName,
        String lastName,
        String jobTitle,
        String primaryExpertise
) {
    public ProfileOverviewOutDTO(Profile profile){
        this(
                profile.getId(),
                profile.getOwner()
                        .getFirstName(),
                profile.getOwner()
                        .getLastName(),
                profile.getJobTitle(),
                profile.getPrimaryExpertise()
                        .toString()
        );
    }
}
