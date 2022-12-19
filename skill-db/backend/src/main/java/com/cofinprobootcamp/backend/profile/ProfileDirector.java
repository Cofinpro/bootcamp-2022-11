package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.user.User;

import java.util.Set;

public class ProfileDirector {
    public static Profile CreateInDTOToEntity(ProfileCreateInDTO profileInDTO, User user, Set<Skill> skillSet) {
        return Profile.builder()
                .firstName(profileInDTO.firstName())
                .lastName(profileInDTO.lastName())
                .jobTitle(profileInDTO.jobTitle())
                .phoneNumber(profileInDTO.phoneNumber())
                .degree(profileInDTO.degree())
                .primaryExpertise(convertFromStringOrThrow(profileInDTO.primaryExpertise()))
                .referenceText(profileInDTO.referenceText())
                .skillSet(skillSet)
                .birthDate(profileInDTO.birthDate())
                .owner(user)
                .build();
    }

    public static Profile UpdateInDTOToEntity(ProfileUpdateInDTO profileInDTO, Profile current, Set<Skill> skillSet) {
        current.setFirstName(profileInDTO.firstName());
        current.setLastName(profileInDTO.lastName());
        current.setJobTitle(profileInDTO.jobTitle());
        current.setDegree(profileInDTO.degree());
        current.setPrimaryExpertise(convertFromStringOrThrow(profileInDTO.primaryExpertise()));
        current.setReferenceText(profileInDTO.referenceText());
        current.setSkillSet(skillSet);
        current.setPhoneNumber(profileInDTO.phoneNumber());
        return current;
    }

    private static Expertises convertFromStringOrThrow(String fullName) {
        Expertises exp = Expertises.fromFullNameString(fullName);
        if (!exp.equals(Expertises.UNDEFINED)) {
            return exp;
        } else {
            throw new RuntimeException("Invalid expertises type specified!");
        }
    }
}
