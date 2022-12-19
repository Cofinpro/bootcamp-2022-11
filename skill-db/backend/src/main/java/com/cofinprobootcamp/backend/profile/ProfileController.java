package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * @param profileInDTO
     * creates profile in database if email of user exists
     */
    @PostMapping(path = "")
    public void createProfile(@RequestBody ProfileCreateInDTO profileInDTO) {
        profileService.createProfileAndAssignToUser(profileInDTO);
    }

    /**
     * @param id
     * @param profileInDTO
     * updates profile by Id
     */
    @PatchMapping(path = "/{id}")
    public void updateProfile(@PathVariable Long id, @RequestBody ProfileUpdateInDTO profileInDTO) {
        profileService.updateProfile(profileInDTO, id);
    }

    /**
     * @param id
     * delete profile by Id
     */
    @DeleteMapping(path = "/{id}")
    public void deleteProfileById(@PathVariable Long id) {
        profileService.deleteProfileById(id);
    }

    /**
     * @param id
     * @return profile detail view
     */
    @GetMapping(path = "/{id}")
    public ProfileDetailsOutDTO getProfile(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }

    /**
     * @return get all overview DTOs
     */
    @GetMapping(path = "")
    public List<ProfileOverviewOutDTO> getAllProfileOverviews() {
        return profileService.getAllOverviewDTOs();
    }
}
