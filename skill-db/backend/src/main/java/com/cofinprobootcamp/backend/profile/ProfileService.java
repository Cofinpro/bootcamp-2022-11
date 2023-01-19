package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.config.Constants;
import com.cofinprobootcamp.backend.email.EmailSendService;
import com.cofinprobootcamp.backend.enums.Expertises;
import com.cofinprobootcamp.backend.exceptions.*;
import com.cofinprobootcamp.backend.exceptions.ProfileAlreadyExistsException;
import com.cofinprobootcamp.backend.image.Image;
import com.cofinprobootcamp.backend.image.ImageService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final SkillService skillService;
    private final JobTitleService jobTitleService;
    private final EmailSendService emailSendService;
    private final ImageService imageService;

    public ProfileService(ProfileRepository profileRepository,
                          SkillService skillService,
                          JobTitleService jobTitleService,
                          EmailSendService emailSendService,
                          ImageService imageService) {
        this.profileRepository = profileRepository;
        this.skillService = skillService;
        this.jobTitleService = jobTitleService;
        this.emailSendService = emailSendService;
        this.imageService = imageService;
    }

    public Profile createProfile(ProfileCreateInDTO profileInDTO, User user)
            throws JobTitleNotFoundException, ProfileAlreadyExistsException, ImageFormatNotAllowedException, ExpertiseNotFoundException
    {
        JobTitle jobTitle = jobTitleService.findJobTitleIfExistsElseThrowException(profileInDTO.jobTitle());

        Set<Skill> skillSet = skillService.findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Image profilePic = imageService.saveImage(profileInDTO.profilePic());
        Profile profile = ProfileDirector
                .CreateInDTOToEntity(profileInDTO, user, skillSet, jobTitle, profilePic);
        if (profileRepository.findProfileByOwner(user).isPresent()) {
            throw new ProfileAlreadyExistsException();
        }
        try {
            tryToSetUniqueOuterId(profile);
            profile = profileRepository.saveAndFlush(profile);
        } catch (Exception e) {
            String msg = "Das Profil konnte nicht gespeichert werden. Ursache könnte möglicherweise eine Race Condition sein. Bitte erneut versuchen!";
            throw new InternalOperationFailedException(msg, e);
        }
        return profile;
    }


    public Profile updateProfile(ProfileUpdateInDTO profileInDTO, String outerId)
            throws ProfileNotFoundException, JobTitleNotFoundException, MailNotSentException, ImageFormatNotAllowedException, ExpertiseNotFoundException {
        Profile current = profileRepository.findFirstByOuterId(outerId).orElseThrow(ProfileNotFoundException::new);
        JobTitle jobTitle = jobTitleService.findJobTitleIfExistsElseThrowException(profileInDTO.jobTitle());
        Set<Skill> skillSet = skillService.findSkillIfExistsElseCreateSkill(profileInDTO.skills());
        Image image = imageService.updateImageIfGiven(profileInDTO.profilePic(), current.getProfilePic().getId());
        Profile profile = ProfileDirector.UpdateInDTOToEntity(profileInDTO, current, skillSet, jobTitle, image);
        profile = profileRepository.saveAndFlush(profile);
        //mailsending
        try {
            String mailRecipientAddress = current.getOwner().getUsername();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (!mailRecipientAddress.equals(authentication.getName())) {
                // trigger info-email
                emailSendService.sendProfileUpdateMail(profile.getFullName(), authentication.getName(), mailRecipientAddress);
            }
        } catch (Exception e) {
            throw new MailNotSentException();
        }
        return profile;
    }


    @Transactional
    public void deleteProfileByOuterId(String outerId) throws ProfileNotFoundException {
        Profile profile = profileRepository.findFirstByOuterId(outerId).orElseThrow(ProfileNotFoundException::new); // Check may be unnecessary
        imageService.deleteImageById(profile.getProfilePic().getId());
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
