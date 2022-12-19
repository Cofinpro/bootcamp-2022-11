package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.skills.SkillRepository;
import com.cofinprobootcamp.backend.skills.SkillService;
import com.cofinprobootcamp.backend.user.UserRepository;
import com.cofinprobootcamp.backend.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final SkillService skillService;

    public ProfileService(ProfileRepository profileRepository,
                          SkillService skillService) {
        this.profileRepository = profileRepository;
        this.skillService = skillService;
    }

    public Profile createProfile(ProfileCreateInDTO profileInDTO, User user) {
        Set<Skill> skillSet = skillService.findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Profile profile = ProfileDirector.CreateInDTOToEntity(profileInDTO, user, skillSet);
        return profileRepository.saveAndFlush(profile);
    }

    //changing email does not work since
    // id of user is not given to frontend here!
    // --> should give back "outer id" of profile and update that way!
    public void updateProfile(ProfileUpdateInDTO profileInDTO, Long outerId) {
        // In theory: convert outerId to internal id
        Profile current = profileRepository.findById(outerId).orElseThrow(RuntimeException::new);
        Set<Skill> skillSet = skillService.findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Profile profile = ProfileDirector.UpdateInDTOToEntity(profileInDTO, current, skillSet);
        profileRepository.saveAndFlush(profile);
    }

    public void deleteProfileById(Long id) {
        profileRepository.deleteById(id);
    }

    public ProfileDetailsOutDTO getProfileById(Long id) {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        return new ProfileDetailsOutDTO(
                profileOptional.orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                )
        );
    }

    public List<ProfileOverviewOutDTO> getAllOverviewDTOs() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream().map(ProfileOverviewOutDTO::new).toList();
    }
}
