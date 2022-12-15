package com.cofinprobootcamp.backend.profile;

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
