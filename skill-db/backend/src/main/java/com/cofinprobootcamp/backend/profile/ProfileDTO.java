package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.user.User;

import java.util.List;

public record ProfileDTO(
        String jobTitle,
        String degree,
        Expertises primaryExpertise,
        String referenceText,
        float version,
        boolean archived,
        boolean isOpen,
        List<User> editUsers,
        List<String> skills,
        User owner
) {

    public ProfileDTO(Profile profile) {
        this(
                profile.getJobTitle(),
                profile.getDegree(),
                profile.getPrimaryExpertise(),
                profile.getReferenceText(),
                profile.getVersion(),
                profile.isArchived(),
                profile.isOpen(),
                profile.getEditUsers(),
                profile.getSkills(),
                profile.getOwner()
        );
    }

}
