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
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

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

 /*
    Test does not compile because spring-boot starter test does not inlude Static mocks!
    @Test
    void createProfile() {
        ProfileCreateInDTO profileCreateInDTO = new ProfileCreateInDTO(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                List.of(""),
                "",
                LocalDate.of(2020,10,10)
        );
        Set<Skill> skills = Set.of(new Skill(""));
        User user = new User();
        Mockito.when(skillService.findSkillIfExistsElseCreateSkill(profileCreateInDTO.skills()))
                .thenReturn(skills);
        MockedStatic<ProfileDirector> mockDirector = Mockito.mockStatic(ProfileDirector.class);
        mockDirector.when(() -> ProfileDirector.CreateInDTOToEntity(profileCreateInDTO, user, skills))
                        .thenReturn(profile);
        ArgumentCaptor<Profile> profileArgumentCaptor = ArgumentCaptor.forClass(Profile.class);
        profileService.createProfile(profileCreateInDTO,user);
        Mockito.verify(profileRepository.save(profileArgumentCaptor.capture()), times(1));
        assertThat(profileArgumentCaptor.getValue()).isEqualTo(profile);
    }
    */

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
