package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.DTO.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.DTO.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.DTO.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.user.UserRepository;
import com.cofinprobootcamp.backend.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private ProfileRepository profileRepository;
    private UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository,
    UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public void createProfileAndUpdateUser(ProfileCreateInDTO profileInDTO) {
        //TODO: replace RuntimeException by custom exception!

        User user = userRepository.findUserByEmail(profileInDTO.email())
                .orElseThrow(RuntimeException::new);
        user.setPhoneNumber(profileInDTO.phoneNumber());
        user.setFirstName(profileInDTO.firstName());
        user.setLastName(profileInDTO.lastName());
        user.setEmail(profileInDTO.email());
        user.setBirthDate(profileInDTO.birthDate());

        Profile profile = ProfileDirector.DTOToEntity(profileInDTO,user);

        profile = profileRepository.saveAndFlush(profile);
        user.setProfile(profile);
        userRepository.saveAndFlush(user);
    }
    //changing email does not work since
    // id of user is not given to frontend here!
    public void updateProfileAndUpdateUser(ProfileCreateInDTO profileInDTO,
                                           Long id) {
        User user = userRepository.findUserByEmail(profileInDTO.email())
                .orElseThrow(RuntimeException::new);
        user.setPhoneNumber(profileInDTO.phoneNumber());
        user.setFirstName(profileInDTO.firstName());
        user.setLastName(profileInDTO.lastName());
        user.setBirthDate(profileInDTO.birthDate());

        Profile profile = ProfileDirector.DTOToEntity(profileInDTO, user);
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
}
