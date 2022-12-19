package com.cofinprobootcamp.backend.profile.dto;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.skills.Skill;

import java.util.List;

public record ProfileDetailsOutDTO(Long outerId, String jobTitle, String degree, String primaryExpertise,
                                   String referenceText, List<String> skills, String firstName, String lastName,
                                   String birthDate, Integer age) {

    public ProfileDetailsOutDTO(Profile profile) {
        this(
                profile.getId(),
                profile.getJobTitle(),
                profile.getDegree(),
                profile.getPrimaryExpertise().toFullNameString(),
                profile.getReferenceText(),
                profile.getSkillSet().stream()
                        .map(Skill::getName)
                        .toList(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getBirthDate().toString(), // ISO Format String as specified in frontend
                profile.getAge()
        );
    }

}
