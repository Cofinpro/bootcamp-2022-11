package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.exceptions.*;
import com.cofinprobootcamp.backend.profile.dto.*;
import com.cofinprobootcamp.backend.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cofinprobootcamp.backend.user.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    /**
     * @param profileInDTO creates profile in database if authorized (401.UNAUTHORIZED)
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    @PreAuthorize("hasPermission(#profileInDTO, @authorityPrefix + 'PROFILES_POST_NEW')")
    public void createProfile(@RequestBody @Valid ProfileCreateInDTO profileInDTO) throws JobTitleNotFoundException, ProfileAlreadyExistsException, ImageFormatNotAllowedException {
        User user = userService.getUserByUsername(profileInDTO.email());
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
            throws ProfileNotFoundException, JobTitleNotFoundException, MailNotSentException, ImageFormatNotAllowedException {
        Profile profile = profileService.updateProfile(profileInDTO, id);
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
     *
     * @param response to get request
     * @throws IOException if response is not writable
     * @throws IllegalAccessException should never be thrown!
     */
    @GetMapping("/export")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'PROFILES_EXPORT_GET_ALL')")
    public void exportAllToExcel(HttpServletResponse response)
            throws IOException, IllegalAccessException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=profiles.xlsx";
        response.setHeader(headerKey, headerValue);
        ExcelGenerator excelGenerator = new ExcelGenerator(profileService.getAllDetailDTOs());
        excelGenerator.createExcel(response.getOutputStream());
    }

    /**
     *
     * @param file to import from.
     *             CSV in GERMAN format (aka semicolon separated values)
     * @throws IOException not
     * @throws JobTitleNotFoundException as in createprofile
     * @throws ProfileAlreadyExistsException as in createprofile
     * @throws CSVFormatException if columns in csv can not be read
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/import",  consumes="multipart/form-data")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'PROFILES_IMPORT_POST_NEW')")
    public void importFromCSV(@RequestParam("file") MultipartFile file)
            throws IOException, JobTitleNotFoundException, ProfileAlreadyExistsException, CSVFormatException, ImageFormatNotAllowedException {
        CSVReader reader = new CSVReader(file, profileService, userService);
        reader.readProfileFromFile();
    }
}
