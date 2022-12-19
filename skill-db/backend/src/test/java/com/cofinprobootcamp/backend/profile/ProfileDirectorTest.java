package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.jobTitle.JobTitle;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
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
                LocalDate.parse("1997-10-10")
        );
        User user = new User();
        user.setEmail("a@b.c");
        Set<Skill> skillSet= Set.of(new Skill());

        Profile profile = ProfileDirector.CreateInDTOToEntity(profileInDTO, user, skillSet, new JobTitle("title"));

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
    }
}
