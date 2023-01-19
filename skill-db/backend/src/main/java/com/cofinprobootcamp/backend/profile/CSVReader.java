package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.exceptions.*;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CSVReader {
    private MultipartFile file;
    private ProfileService profileService;
    private UserService userService;

    public CSVReader(MultipartFile file,
                     @Autowired ProfileService profileService,
                     @Autowired UserService userService) {
        this.file = file;
        this.profileService = profileService;
        this.userService = userService;
    }

    /**
     * reads csv from file, validates content and, if content is valid creates profile objects in database.
     *
     * @throws IOException                   (not rly)
     * @throws ImageFormatNotAllowedException from createProfile function
     * @throws CSVFormatException            if csv is not formatted correctly
     */
    public void readProfileFromFile(HttpServletResponse response)
            throws IOException, ImageFormatNotAllowedException, CSVFormatException {
        String content = new String(file.getBytes(), Charset.defaultCharset());
        CSVFormat format = buildCSVFormat();
        int lineCount = 1;
        for (CSVRecord record : CSVParser.parse(content, format)) {
            try {
                saveProfileFromRecord(record, lineCount);
            } catch (ProfileAlreadyExistsException e) {
                handleExceptionsWithoutThrowing(response,
                        String.format("Das Profil in Zeile %d existiert bereits! ", lineCount)
                );
            } catch (JobTitleNotFoundException e) {
                handleExceptionsWithoutThrowing(response,
                        String.format("Der Jobtitel des Profils in Zeile %d existiert nicht! ", lineCount)
                );
            } catch (CSVArgumentNotValidException e) {
                handleExceptionsWithoutThrowing(response,
                        String.format("Zeile %d: %s", lineCount, e.getViolations().toString().replaceAll("(\\[|])", ""))
                );
            } catch (IllegalArgumentException e) {
                throw new CSVFormatException(e.getMessage().split(",")[0]);
            } catch (ExpertiseNotFoundException e) {
                handleExceptionsWithoutThrowing(response,
                        String.format("Die Primärkompetenz des Profils in Zeile %d existiert nicht! ", lineCount)
                );
            }
            lineCount++;
        }
    }

    private void handleExceptionsWithoutThrowing(HttpServletResponse response, String message) throws IOException {
        response.setStatus(400);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.getOutputStream().write(message.getBytes());
    }

    private void validate(ProfileCreateInDTO inDTO, int lineCount) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ProfileCreateInDTO>> violations = validator.validate(inDTO);
        if (violations.size() > 0) {
            throw new CSVArgumentNotValidException(
                    violations
                            .stream()
                            .map(violation -> violation.getMessageTemplate())
                            .toList(),
                    lineCount);
        }
    }

    private Profile saveProfileFromRecord(CSVRecord record, int lineCount)
            throws ProfileAlreadyExistsException, ImageFormatNotAllowedException, JobTitleNotFoundException, ExpertiseNotFoundException {
        ProfileCreateInDTO inDTO = new ProfileCreateInDTO(
                record.get("Email"),
                record.get("Vorname"),
                record.get("Nachname"),
                record.get("JobTitel"),
                record.get("Abschluss"),
                record.get("Primaerkompetenz"),
                record.get("Referenzen"),
                Arrays.stream(record.get("Skills").split(",")).toList(),
                record.get("Telefonnummer"),
                record.get("Geburtsdatum"),
                null
        );
        validate(inDTO, lineCount);
        User user = userService.getUserByUsername(inDTO.email());
        Profile profile = profileService.createProfile(inDTO, user);
        userService.assignProfileToUser(user, profile);
        return profile;
    }

    private CSVFormat buildCSVFormat() {
        return CSVFormat.EXCEL
                .builder()
                .setHeader()
                .setDelimiter(';')
                .setSkipHeaderRecord(true)
                .build();
    }

}
