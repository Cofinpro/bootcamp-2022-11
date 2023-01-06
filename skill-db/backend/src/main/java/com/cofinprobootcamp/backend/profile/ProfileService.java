package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.config.Constants;
import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.exceptions.UserCreationFailedException;
import com.cofinprobootcamp.backend.jobTitle.JobTitle;
import com.cofinprobootcamp.backend.jobTitle.JobTitleService;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.skills.Skill;
import com.cofinprobootcamp.backend.skills.SkillService;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.utils.RandomStringGenerator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        JobTitle jobTitle = jobTitleService.findJobTitleIfExistsElseThrowException(profileInDTO.jobTitle());
        Set<Skill> skillSet = skillService.findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Profile profile = ProfileDirector.CreateInDTOToEntity(profileInDTO, user, skillSet, jobTitle);
        try {
            tryToSetUniqueOuterId(profile);
            profileRepository.saveAndFlush(profile);
        } catch (Exception e) {
            throw new UserCreationFailedException();
        }
        return profile;
    }

    // changing email does not work since id of user is not given to frontend here!
    // --> should give back "outer id" of profile and update that way!
    public Profile updateProfile(ProfileUpdateInDTO profileInDTO, String outerId)
            throws ProfileNotFoundException, JobTitleNotFoundException {
        // In theory: convert id to internal id
        Profile current = profileRepository.findFirstByOuterId(outerId).orElseThrow(ProfileNotFoundException::new);
        JobTitle jobTitle = jobTitleService.findJobTitleIfExistsElseThrowException(profileInDTO.jobTitle());
        Set<Skill> skillSet = skillService.findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Profile profile = ProfileDirector.UpdateInDTOToEntity(profileInDTO, current, skillSet, jobTitle);
        return profileRepository.saveAndFlush(profile);
    }

    @Transactional
    public void deleteProfileByOuterId(String outerId) throws ProfileNotFoundException {
        profileRepository.findFirstByOuterId(outerId).orElseThrow(ProfileNotFoundException::new); // Check may be unnecessary
        profileRepository.deleteByOuterId(outerId);
    }

    public Profile getProfileByOuterId(String outerId) throws ProfileNotFoundException{
        return profileRepository.findFirstByOuterId(outerId).orElseThrow(ProfileNotFoundException::new);
    }

    public ProfileDetailsOutDTO getProfileDTOById(String outerId) throws ProfileNotFoundException {
        Optional<Profile> profileOptional = profileRepository.findFirstByOuterId(outerId);
        return new ProfileDetailsOutDTO(profileOptional.orElseThrow(ProfileNotFoundException::new));
    }

    public List<ProfileOverviewOutDTO> getAllOverviewDTOs() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                .map(ProfileOverviewOutDTO::new)
                .toList();
    }
    public List<ProfileDetailsOutDTO> getAllDetailDTOs() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                .map(ProfileDetailsOutDTO::new)
                .toList();
    }
    public List<String> getAllExpertises() {
        return Expertises.getAllDefinedValuesAsString();
    }

    private void tryToSetUniqueOuterId(Profile profile) {
        String candidateId = RandomStringGenerator.nextOuterId(Constants.PROFILE_OUTER_ID_LENGTH);
        Optional<Profile> profileOptional = profileRepository.findFirstByOuterId(candidateId);
        if (profileOptional.isPresent()) {
            System.out.println("Collision on id generation [Profile]! Must roll again.");
            tryToSetUniqueOuterId(profile);
            return;
        }
        profile.setOuterId(candidateId);
    }
}
