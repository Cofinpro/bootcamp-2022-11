package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.user.UserService;
import org.springframework.web.bind.annotation.*;
import com.cofinprobootcamp.backend.user.User;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;
    public ProfileController(ProfileService profileService,
                             UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    /**
     * @param profileInDTO
     * creates profile in database if authorized
     */
    @PostMapping(path = "")
    public void createProfile(@RequestBody ProfileCreateInDTO profileInDTO)
            throws JobTitleNotFoundException {
        User user = userService.getUserByEmail(profileInDTO.email());
        Profile profile = profileService.createProfile(profileInDTO, user);
        userService.assignProfileToUser(user,profile);
    }

    /**
     * @param id id of profile
     * @param profileInDTO profile object with changes
     * updates profile by Id
     */
    @PatchMapping(path = "/{id}")
    public void updateProfile(@PathVariable Long id,
                              @RequestBody ProfileUpdateInDTO profileInDTO)
            throws ProfileNotFoundException, JobTitleNotFoundException {
        profileService.updateProfile(profileInDTO, id);
    }

    /**
     * @param id
     * delete profile by Id
     */
    @DeleteMapping(path = "/{id}")
    public void deleteProfileById(@PathVariable Long id)
            throws ProfileNotFoundException {
        profileService.deleteProfileById(id);
    }

    /**
     * @param id
     * @return profile detail view
     */
    @GetMapping(path = "/{id}")
    public ProfileDetailsOutDTO getProfile(@PathVariable Long id)
            throws ProfileNotFoundException {
        return profileService.getProfileById(id);
    }

    /**
     * @return get all overview DTOs
     */
    @GetMapping(path = "")
    public List<ProfileOverviewOutDTO> getAllProfileOverviews() {
        return profileService.getAllOverviewDTOs();
    }

    /**
     * Fetches a list of all defined primary expertises from the server.
     * @return A list of unique {@code String}s representing the types of expertises
     */
    @GetMapping(path = "/expertises")
    public List<String> getAllExpertises() { // makes more sense to have this as an endpoint under profiles, where content is actually stored
        return profileService.getAllExpertises();
    }
}
