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
     * Endpoint to create a profile in the database from the given information.
     *
     * @param profileInDTO The {@link ProfileCreateInDTO} representation of a profile.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    @PreAuthorize("hasPermission(#profileInDTO, @authorityPrefix + 'PROFILES_POST_NEW')")
    public void createProfile(@RequestBody @Valid ProfileCreateInDTO profileInDTO)
            throws JobTitleNotFoundException, ProfileAlreadyExistsException, ImageFormatNotAllowedException, ExpertiseNotFoundException {
        User user = userService.getUserByUsername(profileInDTO.email());
        Profile profile = profileService.createProfile(profileInDTO, user);
        userService.assignProfileToUser(user, profile);
    }

    /**
     * Endpoint to update a profile, that already exists in the database.
     *
     * @param id           The id of the potentially existing {@link Profile}
     * @param profileInDTO The {@link ProfileUpdateInDTO} representation of a {@link Profile} with changes
     */
    @PatchMapping(path = "/{id}")
    @PreAuthorize("hasPermission(#id, @authorityPrefix + 'PROFILES_PATCH_BY_ID')")
    public void updateProfile(@PathVariable String id, @RequestBody @Valid ProfileUpdateInDTO profileInDTO)
            throws ProfileNotFoundException, JobTitleNotFoundException, MailNotSentException, ImageFormatNotAllowedException, ExpertiseNotFoundException {
        profileService.updateProfile(profileInDTO, id);
    }

    /**
     * Endpoint to delete a specific profile identified by outerId.
     *
     * @param id The outerId of a possibly existing {@link Profile}
     */
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasPermission(#id, 'void', @authorityPrefix + 'PROFILES_DELETE_BY_ID')")
    public void deleteProfileById(@PathVariable String id)
            throws ProfileNotFoundException {
        Profile profile = profileService.getProfileByOuterId(id);
        userService.detachProfileFromUser(profile.getOwner().getId());
        profileService.deleteProfileByOuterId(id);
    }

    /**
     * Endpoint to get a profile by its outerId.
     * @param id The outerId of a possible existing {@link Profile}
     * @return A {@link ProfileDetailsOutDTO} representation of a {@link Profile}
     */
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasPermission(#id, 'ProfileDetailsOutDTO', @authorityPrefix + 'PROFILES_GET_BY_ID')")
    public ProfileDetailsOutDTO getProfile(@PathVariable String id)
            throws ProfileNotFoundException {
        return profileService.getProfileDTOById(id);
    }

    /**
     * Endpoint to get all short information of existing profiles to display in the overview.
     * @return A {@link List} of {@link ProfileOverviewOutDTO} representations of a shorted {@link Profile}
     */
    @GetMapping(path = "")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'PROFILES_GET_ALL')")
    public List<ProfileOverviewOutDTO> getAllProfileOverviews() {
        return profileService.getAllOverviewDTOs();
    }

    /**
     * Endpoint to get a list of all defined primary expertises from the database.
     *
     * @return A {@link List} of unique {@link String}s representing the types of expertises
     */
    @GetMapping(path = "/expertises")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'PROFILES_EXPERTISES_GET_ALL')")
    public List<String> getAllExpertises() {
        return profileService.getAllExpertises();
    }

    /**
     * Endpoint to generate an Excel file.
     *
     * @param response to get request
     * @throws IOException if response is not writable
     */
    @GetMapping("/export")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'PROFILES_EXPORT_GET_ALL')")
    public void exportAllToExcel(HttpServletResponse response)
            throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=profiles.xlsx";
        response.setHeader(headerKey, headerValue);
        ExcelGenerator excelGenerator = new ExcelGenerator(profileService.getAllDetailDTOs());
        excelGenerator.createExcel(response.getOutputStream());
    }

    /**
     *Endpoint to import from a CSV file in GERMAN format (aka semicolon seperated values).
     * @param file to import from
     * @throws IOException not
     * @throws ImageFormatNotAllowedException as in createProfile
     * @throws CSVFormatException if columns in csv can not be read
     */
    @PostMapping(path="/import",  consumes="multipart/form-data")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'PROFILES_IMPORT_POST_NEW')")
    public void importFromCSV(@RequestParam("file") MultipartFile file, HttpServletResponse response)
            throws IOException, CSVFormatException, ImageFormatNotAllowedException {
        CSVReader reader = new CSVReader(file, profileService, userService);
        reader.readProfileFromFile(response);
    }
}
