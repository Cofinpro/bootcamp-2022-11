package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserDTO;
import com.cofinprobootcamp.backend.user.UserService;
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

    @DeleteMapping(path = "")
    public void deleteProfile(@RequestBody Profile profile){
        profileService.deleteProfile(profile);
    }

    @GetMapping(path = "/")
    public ProfileDTO getProfile(@RequestParam Long id) {
        return profileService.getProfileById(id);
    }

    @GetMapping(path = "")
    public List<ProfileDTO> getAllProfiles() {
        return profileService.getAllProfiles();
    }
}
