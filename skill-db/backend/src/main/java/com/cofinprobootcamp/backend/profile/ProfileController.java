package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.auth.UserDetailsImpl;
import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
import com.cofinprobootcamp.backend.exceptions.ProfileAlreadyExistsException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.user.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import com.cofinprobootcamp.backend.user.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;
    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;

    public ProfileController(ProfileService profileService, UserService userService,
                             ProfileRepository profileRepository, AuthenticationManager authenticationManager) {
        this.profileService = profileService;
        this.userService = userService;
        this.profileRepository = profileRepository;
        this.authenticationManager = authenticationManager;
    }

    /**
     * @param profileInDTO creates profile in database if authorized (401.UNAUTHORIZED)
     */
    @PostMapping(path = "")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER', 'SCOPE_ROLE_HR')")
    public void createProfile(@RequestBody @Valid ProfileCreateInDTO profileInDTO) throws JobTitleNotFoundException, ProfileAlreadyExistsException {
        User user = userService.getUserByUsername(profileInDTO.email());
        if (profileRepository.findProfileByOwner(user).isPresent()) {
            throw new ProfileAlreadyExistsException();
        }
        Profile profile = profileService.createProfile(profileInDTO, user);
        userService.assignProfileToUser(user, profile);
    }

    /**
     * @param id           id of profile
     * @param profileInDTO profile object with changes
     *                     updates profile by Id
     */
    @PatchMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER', 'SCOPE_ROLE_HR')")
    public void updateProfile(@PathVariable Long id, @RequestBody @Valid ProfileUpdateInDTO profileInDTO)
            throws ProfileNotFoundException, JobTitleNotFoundException {
        profileService.updateProfile(profileInDTO, id);
    }

    /**
     * @param id delete profile by ID (This expects an outerId)
     */
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER', 'SCOPE_ROLE_HR')")
    public void deleteProfileById(@PathVariable String id) throws ProfileNotFoundException {
        Profile profile = profileService.getProfileByOuterId(id); // Find profile by its outerId
        userService.detachProfileFromUser(profile.getOwner().getId());
        profileService.deleteProfileByOuterId(id);
    }

    /**
     * @param id outer profile ID
     * @return profile detail view
     */
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER', 'SCOPE_ROLE_HR')")
    public ProfileDetailsOutDTO getProfile(@PathVariable String id) throws ProfileNotFoundException {
        return profileService.getProfileDTOById(id);
    }

    /**
     * @return get all overview DTOs
     */
    @GetMapping(path = "")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER', 'SCOPE_ROLE_HR')")
    public List<ProfileOverviewOutDTO> getAllProfileOverviews() {
        return profileService.getAllOverviewDTOs();
    }

    /**
     * Fetches a list of all defined primary expertises from the server.
     *
     * @return A list of unique {@code String}s representing the types of expertises
     */
    @GetMapping(path = "/expertises")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER', 'SCOPE_ROLE_HR')")
    public List<String> getAllExpertises() { // makes more sense to have this as an endpoint under profiles, where content is actually stored
        return profileService.getAllExpertises();
    }
}
