package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.exceptions.ExpertiseNotFoundException;
import com.cofinprobootcamp.backend.image.Image;
import com.cofinprobootcamp.backend.jobTitle.JobTitle;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.user.User;

import java.time.LocalDate;
import java.util.Set;

public class ProfileDirector {
    public static Profile CreateInDTOToEntity(ProfileCreateInDTO profileInDTO,
                                              User user,
                                              Set<Skill> skillSet,
                                              JobTitle jobTitle,
                                              Image profilePic) throws ExpertiseNotFoundException {
        return Profile.builder()
                .firstName(profileInDTO.firstName())
                .lastName(profileInDTO.lastName())
                .jobTitle(jobTitle)
                .phoneNumber(profileInDTO.phoneNumber())
                .degree(profileInDTO.degree())
                .primaryExpertise(convertFromStringOrThrow(profileInDTO.primaryExpertise()))
                .referenceText(profileInDTO.referenceText())
                .skillSet(skillSet)
                .birthDate(LocalDate.parse(profileInDTO.birthDate()))
                .owner(user)
                .profilePic(profilePic)
                .build();
    }

    public static Profile UpdateInDTOToEntity(ProfileUpdateInDTO profileInDTO,
                                              Profile current,
                                              Set<Skill> skillSet,
                                              JobTitle jobTitle,
                                              Image image) throws ExpertiseNotFoundException {
        current.setFirstName(profileInDTO.firstName());
        current.setLastName(profileInDTO.lastName());
        current.setJobTitle(jobTitle);
        current.setBirthDate(LocalDate.parse(profileInDTO.birthDate()));
        current.setDegree(profileInDTO.degree());
        current.setPrimaryExpertise(convertFromStringOrThrow(profileInDTO.primaryExpertise()));
        current.setReferenceText(profileInDTO.referenceText());
        current.setSkillSet(skillSet);
        current.setPhoneNumber(profileInDTO.phoneNumber());
        current.setProfilePic(image);
        return current;
    }

    private static Expertises convertFromStringOrThrow(String fullName)
    throws ExpertiseNotFoundException {
        Expertises exp = Expertises.fromFullNameString(fullName);
        if (!exp.equals(Expertises.UNDEFINED)) {
            return exp;
        } else {
            throw new ExpertiseNotFoundException(); // Custom  would be desired here
        }
    }
}
