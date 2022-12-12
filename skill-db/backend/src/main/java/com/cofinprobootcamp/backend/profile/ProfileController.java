package com.cofinprobootcamp.backend.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping(path = "")
    public void createProfile(@RequestBody Profile profile) {
        profileService.createProfile(profile);
    }

    @PutMapping(path = "")
    public void updateProfile(@RequestBody Profile profile) {
        profileService.updateProfile(profile);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProfileById(@PathVariable Long id){
        profileService.deleteProfileById(id);
    }

    @GetMapping(path = "/{id}")
    public ProfileDTO getProfile(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }

    @GetMapping(path = "")
    public List<ProfileDTO> getAllProfiles() {
        return profileService.getAllProfiles();
    }
}
