package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.image.Image;
import com.cofinprobootcamp.backend.jobTitle.JobTitle;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ProfileDirectorTest {

    @Test
    void createInDTOToEntity() {
        ProfileCreateInDTO profileInDTO = new ProfileCreateInDTO(
                "email@email.de",
                "first",
                "last",
                "title",
                "degree",
                Expertises.SPEC.toFullNameString(),
                "references",
                List.of("skill"),
                "12345678901",
                "1997-10-10",
                0L
        );
        User user = new User();
        user.setUsername("a@b.c");
        Set<Skill> skillSet= Set.of(new Skill());

        Profile profile = ProfileDirector.CreateInDTOToEntity(profileInDTO, user, skillSet, new JobTitle("title"), new Image());

        assertThat(profile.getBirthDate()).isEqualTo(profileInDTO.birthDate());
        assertThat(profile.getFirstName()).isEqualTo(profileInDTO.firstName());
        assertThat(profile.getLastName()).isEqualTo(profileInDTO.lastName());
        assertThat(profile.getJobTitle().getName()).isEqualTo(profileInDTO.jobTitle());
        assertThat(profile.getDegree()).isEqualTo(profileInDTO.degree());
        assertThat(profile.getOwner()).isEqualTo(user);
        assertThat(profile.getPrimaryExpertise()).isEqualTo(
                Expertises.fromFullNameString(profileInDTO.primaryExpertise()));
        assertThat(profile.getPhoneNumber()).isEqualTo(profileInDTO.phoneNumber());
        assertThat(profile.getSkillSet()).isEqualTo(skillSet);
    }

    @Test
    void updateInDTOToEntity() {
        ProfileUpdateInDTO inDTO = new ProfileUpdateInDTO(
                "first",
        "last",
        "title",
        "degree",
        "Technologie",
        "reference",
        List.of("skill"),
        "12345678901",
        "2020-10-10");
        Profile oldProfile = new Profile().builder()
                .id(1L)
                .owner(new User(1L, "00000","a","b",false,null,null))
                .firstName("firstOld")
                .lastName("lastOld")
                .jobTitle(new JobTitle("jobOld"))
                .phoneNumber("12345667761")
                .primaryExpertise(Expertises.SPEC)
                .referenceText("this is a ref")
                .build();
        Set<Skill> skillSet= Set.of(new Skill(inDTO.skills().get(0)));
        JobTitle jobTitle = new JobTitle(inDTO.jobTitle());
        Profile newProfile = ProfileDirector.UpdateInDTOToEntity(inDTO,oldProfile,
                skillSet,jobTitle);
        //owner does not change
        assertThat(newProfile.getOwner().getUsername())
                .isEqualTo(oldProfile.getOwner().getUsername());
        assertThat(newProfile.getId()).isEqualTo(oldProfile.getId());

        //things that do change
        assertThat(newProfile.getLastName()).isEqualTo(inDTO.lastName());
        assertThat(newProfile.getFirstName()).isEqualTo(inDTO.firstName());
        assertThat(newProfile.getDegree()).isEqualTo(inDTO.degree());
        assertThat(newProfile.getPhoneNumber()).isEqualTo(inDTO.phoneNumber());
        assertThat(newProfile.getPrimaryExpertise())
                .isEqualTo(Expertises.fromFullNameString(inDTO.primaryExpertise()));
     //  assertThat(newProfile.getBirthDate()).isEqualTo(inDTO.birthDate());
        assertThat(newProfile.getReferenceText()).isEqualTo(inDTO.referenceText());
        assertThat(newProfile.getSkillSet()).isEqualTo(skillSet);
        assertThat(newProfile.getJobTitle()).isEqualTo(jobTitle);
    }
}
