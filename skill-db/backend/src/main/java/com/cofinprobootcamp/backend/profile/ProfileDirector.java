package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.user.User;

public class ProfileDirector {
    public static Profile DTOToEntity(ProfileCreateInDTO profileInDTO, User user) {
        return Profile.builder()
                .firstName(profileInDTO.firstName())
                .lastName(profileInDTO.lastName())
                .jobTitle(profileInDTO.jobTitle())
                .phoneNumber(profileInDTO.phoneNumber())
                .degree(profileInDTO.degree())
                .primaryExpertise(profileInDTO.primaryExpertise())
                .skills(profileInDTO.skills())
                .referenceText(profileInDTO.referenceText())
                .birthDate(profileInDTO.birthDate())
                .owner(user)
                .build();
    }
}
