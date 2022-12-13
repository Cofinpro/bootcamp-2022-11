package com.cofinprobootcamp.backend.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profile")
public class ProfileController {
    //Field Injection is not recommended, you can not unit test this!
    //Better to use Constructor based injection
    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

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

    //Access Point not tested bc I don't know how with JWT
    @GetMapping(path = "")
    public List<OverviewDTO> getAllProfileOverviews() {
        return profileService.getAllOverviewDTOs();
    }
}
