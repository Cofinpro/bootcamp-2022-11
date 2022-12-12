package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.user.User;

import java.util.List;

public record ProfileDTO(
        Long id,
        String jobTitle,
        String degree,
        Expertises primaryExpertise,
        String referenceText,
        List<String> skills,
        User owner
) {

    public ProfileDTO(Profile profile) {
        this(
                profile.getId(),
                profile.getJobTitle(),
                profile.getDegree(),
                profile.getPrimaryExpertise(),
                profile.getReferenceText(),
                profile.getSkills(),
                profile.getOwner()
        );
    }

}
