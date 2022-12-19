package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.skills.SkillRepository;
import com.cofinprobootcamp.backend.skills.SkillService;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ProfileServiceTest {
    ProfileService profileService;
    @Mock
    ProfileRepository profileRepository;
    @Mock
    SkillService skillService;
    Profile profile;

    @BeforeEach
    public void initialize(){
        MockitoAnnotations.openMocks(this);
        profileService = new ProfileService(profileRepository,
                skillService
                );
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(new Skill(" "));
        profile = Profile.builder()
                .firstName("")
                .lastName("")
                .jobTitle("")
                .phoneNumber("")
                .degree("")
                .primaryExpertise(Expertises.SPEC)
                .referenceText("")
                .skillSet(skillSet)
                .birthDate(LocalDate.of(10,10,10))
                .owner(new User())
                .build();
    }



    @Test
    void getProfileById() {
        Mockito.when(profileRepository.findById(1L))
                .thenReturn(Optional.of(profile));

        assertThat(profileService.getProfileById(1L))
                .isEqualTo(new ProfileDetailsOutDTO(profile));
    }

    @Test
    void getAllOverviewDTOs() {

        Mockito.when(profileRepository.findAll())
                .thenReturn(List.of(profile));

        assertThat(profileService.getAllOverviewDTOs())
                .isEqualTo(List.of(new ProfileOverviewOutDTO(profile)));
    }
}
