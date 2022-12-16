package com.cofinprobootcamp.backend.profile.DTO;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.profile.Profile;

import java.util.List;

public record ProfileDetailsOutDTO(
        Long id,
        String jobTitle,
        String degree,
        Expertises primaryExpertise,
        String referenceText,
        List<String> skills,
        String firstName,
        String lastName,
        String birthDate
) {

    public ProfileDetailsOutDTO(Profile profile) {
        this(
                profile.getId(),
                profile.getJobTitle(),
                profile.getDegree(),
                profile.getPrimaryExpertise(),
                profile.getReferenceText(),
                profile.getSkills(),
                profile.getOwner()
                        .getFirstName(),
                profile.getOwner()
                        .getLastName(),
                profile.getOwner()
                        .getBirthDate()
                        .toString() //ISO Format as specified  in frontend, String parsed in frontend
        );
    }

}
