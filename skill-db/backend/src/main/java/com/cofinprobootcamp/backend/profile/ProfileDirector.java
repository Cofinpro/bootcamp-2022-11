package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.user.User;

public class ProfileDirector {
    public static Profile DTOToEntity(ProfileCreateInDTO profileInDTO, User user) {
        return Profile.builder()
                .jobTitle(profileInDTO.jobTitle())
                .degree(profileInDTO.degree())
                .primaryExpertise(profileInDTO.primaryExpertise())
                .skills(profileInDTO.skills())
                .referenceText(profileInDTO.referenceText())
                .owner(user)
                .build();
    }
}
