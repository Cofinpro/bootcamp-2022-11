package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
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

    private ProfileRepository profileRepository;
    private UserRepository userRepository;
    private SkillRepository skillRepository;

    public ProfileService(ProfileRepository profileRepository,
                          UserRepository userRepository,
                          SkillRepository skillRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public void createProfileAndAssignToUser(ProfileCreateInDTO profileInDTO) {
        //TODO: replace RuntimeException by custom exception!

        User user = userRepository.findUserByEmail(profileInDTO.email())
                .orElseThrow(RuntimeException::new);
        Set<Skill> skillSet = findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Profile profile = ProfileDirector.DTOToEntity(profileInDTO, user, skillSet);
        profile.setOwner(user);
        profile = profileRepository.saveAndFlush(profile);
        user.setProfile(profile);
        userRepository.saveAndFlush(user);
    }

    //changing email does not work since
    // id of user is not given to frontend here!

    public void updateProfile(ProfileCreateInDTO profileInDTO,
                              Long id) {
        User user = userRepository.findUserByEmail(profileInDTO.email())
                .orElseThrow(RuntimeException::new);
        Set<Skill> skillSet = findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Profile profile = ProfileDirector.DTOToEntity(profileInDTO, user, skillSet);
        profile.setId(id);
        profileRepository.saveAndFlush(profile);
    }

    public void deleteProfileById(Long id) {
        profileRepository.deleteById(id);
    }

    public ProfileDetailsOutDTO getProfileById(Long id) {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        return new ProfileDetailsOutDTO(profileOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    //Not needed anymore(??)

    public List<ProfileDetailsOutDTO> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream().map(ProfileDetailsOutDTO::new).toList();
    }

    public List<ProfileOverviewOutDTO> getAllOverviewDTOs() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream().map(ProfileOverviewOutDTO::new).toList();
    }

    private Set<Skill> findSkillIfExistsElseCreateSkill(List<String> skillInputs) {
        return skillInputs
                .stream()
                .map(name -> {
                    Optional<Skill> foundSkill = skillRepository.findSkillByName(name);
                    return foundSkill.orElse(skillRepository.save(new Skill(name)));
                })
                .collect(Collectors.toSet());
    }
}
