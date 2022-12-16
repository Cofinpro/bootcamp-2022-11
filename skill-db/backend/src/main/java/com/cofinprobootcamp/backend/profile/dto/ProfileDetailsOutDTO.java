package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.profile.Profile;

import java.util.List;

public record ProfileDetailsOutDTO(
        Long id,
        String jobTitle,
        String degree,
        String primaryExpertise,
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
                profile.getPrimaryExpertise()
                        .toFullNameString(),
                profile.getReferenceText(),
                profile.getSkills(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getBirthDate()
                        .toString() //ISO Format as specified  in frontend, String parsed in frontend
        );
    }

}
