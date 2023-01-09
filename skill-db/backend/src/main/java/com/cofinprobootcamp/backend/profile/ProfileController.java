package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
import com.cofinprobootcamp.backend.exceptions.ProfileAlreadyExistsException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileOverviewOutDTO;
import com.cofinprobootcamp.backend.profile.dto.ProfileUpdateInDTO;
import com.cofinprobootcamp.backend.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cofinprobootcamp.backend.user.User;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileService profileService, UserService userService,
                             ProfileRepository profileRepository) {
        this.profileService = profileService;
        this.userService = userService;
        this.profileRepository = profileRepository;
    }

    /**
     * @param profileInDTO creates profile in database if authorized (401.UNAUTHORIZED)
     */
    @PostMapping(path = "")
    @PreAuthorize("hasPermission(#profileInDTO, @authorityPrefix + 'PROFILES_POST_NEW')")
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
    @PreAuthorize("hasPermission(#id, @authorityPrefix + 'PROFILES_PATCH_BY_ID')")
    public void updateProfile(@PathVariable String id, @RequestBody @Valid ProfileUpdateInDTO profileInDTO)
            throws ProfileNotFoundException, JobTitleNotFoundException {
        profileService.updateProfile(profileInDTO, id);
    }

    /**
     * @param id delete profile by ID (This expects an outerId)
     */
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasPermission(#id, 'void', @authorityPrefix + 'PROFILES_DELETE_BY_ID')")
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
    @PreAuthorize("hasPermission(#id, 'ProfileDetailsOutDTO', @authorityPrefix + 'PROFILES_GET_BY_ID')")
    public ProfileDetailsOutDTO getProfile(@PathVariable String id) throws ProfileNotFoundException {
        return profileService.getProfileDTOById(id);
    }

    /**
     * @return get all overview DTOs
     */
    @GetMapping(path = "")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'PROFILES_GET_ALL')")
    public List<ProfileOverviewOutDTO> getAllProfileOverviews() {
        return profileService.getAllOverviewDTOs();
    }

    /**
     * Fetches a list of all defined primary expertises from the server.
     *
     * @return A list of unique {@code String}s representing the types of expertises
     */
    @GetMapping(path = "/expertises")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'PROFILES_EXPERTISES_GET_ALL')")
    public List<String> getAllExpertises() { // makes more sense to have this as an endpoint under profiles, where content is actually stored
        return profileService.getAllExpertises();
    }

    /**
     * generates excel and writes excel to responses outputstream
     * @param response to get request
     * @throws IOException if response is not writable
     * @throws IllegalAccessException should never be thrown!
     */
    @GetMapping("/export")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_HR')")
    public void exportAllToExcel(HttpServletResponse response)
            throws IOException, IllegalAccessException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        ExcelGenerator excelGenerator = new ExcelGenerator(profileService.getAllDetailDTOs());
        excelGenerator.createExcel(response.getOutputStream());
    }
}
