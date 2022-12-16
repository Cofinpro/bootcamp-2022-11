package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.user.User;

import java.util.Set;

public class ProfileDirector {
    public static Profile DTOToEntity(ProfileCreateInDTO profileInDTO, User user, Set<Skill> skillSet) {
        return Profile.builder()
                .firstName(profileInDTO.firstName())
                .lastName(profileInDTO.lastName())
                .jobTitle(profileInDTO.jobTitle())
                .phoneNumber(profileInDTO.phoneNumber())
                .degree(profileInDTO.degree())
                .primaryExpertise(profileInDTO.primaryExpertise())
                .referenceText(profileInDTO.referenceText())
                .skillSet(skillSet)
                .birthDate(profileInDTO.birthDate())
                .owner(user)
                .build();
    }
}
