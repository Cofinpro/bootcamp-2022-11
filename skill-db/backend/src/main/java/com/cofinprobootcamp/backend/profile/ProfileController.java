package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.DTO.ProfileInDTO;
import com.cofinprobootcamp.backend.profile.DTO.ProfileOutDTO;
import com.cofinprobootcamp.backend.profile.DTO.ProfileOverviewOutDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profile")
public class ProfileController {

    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(path = "")
    public void createProfile(@RequestBody ProfileInDTO profileInDTO) {
        profileService.createProfileAndUpdateUser(profileInDTO);
    }

    @PutMapping(path = "/{id}")
    public void updateProfile(@PathVariable Long id,
                              @RequestBody ProfileInDTO profileInDTO) {
        profileService.updateProfileAndUpdateUser(profileInDTO,id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProfileById(@PathVariable Long id){
        profileService.deleteProfileById(id);
    }

    @GetMapping(path = "/{id}")
    public ProfileOutDTO getProfile(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }

    //Access Point not tested bc I don't know how with JWT
    @GetMapping(path = "")
    public List<ProfileOverviewOutDTO> getAllProfileOverviews() {
        return profileService.getAllOverviewDTOs();
    }
}
