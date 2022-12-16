package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profiles")
public class ProfileController {
    //Field Injection is not recommended, you can not unit test this!
    //Better to use Constructor based injection
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(path = "")
    public void createProfile(@RequestBody ProfileCreateInDTO profileInDTO) {
        profileService.createProfileAndUpdateUser(profileInDTO);
    }

    @PutMapping(path = "/{id}")
    public void updateProfile(@PathVariable Long id,
                              @RequestBody ProfileCreateInDTO profileInDTO) {
        profileService.updateProfileAndUpdateUser(profileInDTO,id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProfileById(@PathVariable Long id){
        profileService.deleteProfileById(id);
    }

    @GetMapping(path = "/{id}")
    public ProfileDetailsOutDTO getProfile(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }

    @GetMapping(path = "")
    public List<ProfileOverviewOutDTO> getAllProfileOverviews() {
        return profileService.getAllOverviewDTOs();
    }
}
