package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.jobTitle.JobTitle;
import com.cofinprobootcamp.backend.jobTitle.JobTitleService;
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
    private final JobTitleService jobTitleService;

    public ProfileService(ProfileRepository profileRepository,
                          SkillService skillService,
                          JobTitleService jobTitleService) {
        this.profileRepository = profileRepository;
        this.skillService = skillService;
        this.jobTitleService = jobTitleService;
    }

    public Profile createProfile(ProfileCreateInDTO profileInDTO, User user) throws JobTitleNotFoundException {
        Set<Skill> skillSet = skillService.findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        JobTitle jobTitle = jobTitleService.findJobTitleIfExistsElseThrowException(profileInDTO.jobTitle());
        Profile profile = ProfileDirector.CreateInDTOToEntity(profileInDTO, user, skillSet, jobTitle);
        return profileRepository.saveAndFlush(profile);
    }

    // changing email does not work since outerId of user is not given to frontend here!
    // --> should give back "outer outerId" of profile and update that way!
    public void updateProfile(ProfileUpdateInDTO profileInDTO, Long outerId)
            throws ProfileNotFoundException, JobTitleNotFoundException {
        // In theory: convert outerId to internal id
        Profile current = profileRepository.findById(outerId).orElseThrow(ProfileNotFoundException::new);
        Set<Skill> skillSet = skillService.findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        JobTitle jobTitle = jobTitleService.findJobTitleIfExistsElseThrowException(profileInDTO.jobTitle());
        Profile profile = ProfileDirector.UpdateInDTOToEntity(profileInDTO, current, skillSet, jobTitle);
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

    public List<String> getAllExpertises() {
        return Expertises.getAllDefinedValuesAsString();
    }
}
