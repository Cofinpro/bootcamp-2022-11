package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.jobTitle.JobTitle;
import com.cofinprobootcamp.backend.jobTitle.JobTitleService;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.skills.SkillService;
import com.cofinprobootcamp.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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
    @Mock
    JobTitleService jobTitleService;
    Profile profile;

    @BeforeEach
    public void initialize(){
        MockitoAnnotations.openMocks(this);
        profileService = new ProfileService(profileRepository,
                skillService,
                jobTitleService
                );
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(new Skill(" "));
        profile = Profile.builder()
                .firstName("")
                .lastName("")
                .jobTitle(new JobTitle(""))
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
    void given_Optional_of_Profile_when_profile_in_db_then_return_profile() throws ProfileNotFoundException {
        Mockito.when(profileRepository.findById(1L))
                .thenReturn(Optional.of(profile));

        assertThat(profileService.getProfileDTOById(1L))
                .isEqualTo(new ProfileDetailsOutDTO(profile));
    }

    @Test
    void given_List_of_profile_when_profile_in_db_then_return_profile_overview_dto() {

        Mockito.when(profileRepository.findAll())
                .thenReturn(List.of(profile));

        assertThat(profileService.getAllOverviewDTOs())
                .isEqualTo(List.of(new ProfileOverviewOutDTO(profile)));
    }

    @Test
    void deleteProfileById() throws ProfileNotFoundException {
        Mockito.when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        profileService.deleteProfileById(1L);
        Mockito.verify(profileRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void getAllOverviewDTOs() {
        List<Profile> profileList =List.of(profile);
        Mockito.when(profileRepository.findAll()).thenReturn(profileList);
        assertThat(profileService.getAllOverviewDTOs().get(0).name())
                .isEqualTo(profile.getFirstName() + " " + profile.getLastName());
    }
}
