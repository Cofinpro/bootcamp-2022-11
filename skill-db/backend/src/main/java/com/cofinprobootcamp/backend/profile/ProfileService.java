package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.skills.SkillService;
import com.cofinprobootcamp.backend.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    // outerId of user is not given to frontend here!
    // --> should give back "outer outerId" of profile and update that way!
    public void updateProfile(ProfileUpdateInDTO profileInDTO, Long outerId) throws ProfileNotFoundException {
        // In theory: convert outerId to internal id
        Profile current = profileRepository.findById(outerId).orElseThrow(ProfileNotFoundException::new);
        Set<Skill> skillSet = skillService.findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Profile profile = ProfileDirector.UpdateInDTOToEntity(profileInDTO, current, skillSet);
        profileRepository.saveAndFlush(profile);
    }

    public void deleteProfileById(Long id) throws ProfileNotFoundException {
        profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        profileRepository.deleteById(id);
    }

    public ProfileDetailsOutDTO getProfileById(Long id) throws ProfileNotFoundException {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        return new ProfileDetailsOutDTO(profileOptional.orElseThrow(ProfileNotFoundException::new));
    }

    public List<ProfileOverviewOutDTO> getAllOverviewDTOs() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                .map(ProfileOverviewOutDTO::new)
                .toList();
    }

    private Set<Skill> findSkillIfExistsElseCreateSkill(List<String> skillInputs) {
        return skillInputs.stream()
                .map(name -> skillRepository.findSkillByName(name)
                                .orElse(skillRepository.save(new Skill(name))))
                .collect(Collectors.toSet());
    }

    public List<String> getAllExpertises() {
        return Expertises.getAllDefinedValuesAsString();
    }
}
