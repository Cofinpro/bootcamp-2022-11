package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.skills.SkillRepository;
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
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public ProfileService(ProfileRepository profileRepository,
                          UserRepository userRepository,
                          SkillRepository skillRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public void createProfileAndAssignToUser(ProfileCreateInDTO profileInDTO) {
        //TODO: replace RuntimeException by custom exception!
        User user = userRepository.findByUsername(profileInDTO.email()).orElseThrow(RuntimeException::new); // Create method that throws descriptive custom exception
        Set<Skill> skillSet = findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Profile profile = ProfileDirector.CreateInDTOToEntity(profileInDTO, user, skillSet);
        profile = profileRepository.saveAndFlush(profile);
        user.setProfile(profile);
        userRepository.saveAndFlush(user);
    }

    //changing email does not work since
    // id of user is not given to frontend here!
    // --> should give back "outer id" of profile and update that way!
    public void updateProfile(ProfileUpdateInDTO profileInDTO, Long outerId) {
        // In theory: convert outerId to internal id
        Profile current = profileRepository.findById(outerId).orElseThrow(RuntimeException::new);
        Set<Skill> skillSet = findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Profile profile = ProfileDirector.UpdateInDTOToEntity(profileInDTO, current, skillSet);
        profile.setId(outerId);
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

    private Set<Skill> findSkillIfExistsElseCreateSkill(List<String> skillInputs) {
        return skillInputs.stream()
                .map(
                        name -> skillRepository.findSkillByName(name)
                                .orElse(skillRepository.save(new Skill(name))))
                .collect(Collectors.toSet());
    }
}
