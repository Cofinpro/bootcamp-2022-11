package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.exceptions.CSVArgumentNotValidException;
import com.cofinprobootcamp.backend.exceptions.CSVFormatException;
import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
import com.cofinprobootcamp.backend.exceptions.ProfileAlreadyExistsException;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.user.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
     * @throws IOException (not rly)
     * @throws JobTitleNotFoundException from createprofile function
     * @throws ProfileAlreadyExistsException from createprofile function
     * @throws CSVFormatException if csv is not formatted correctly
     * @throws CSVArgumentNotValidException if csv records contain nonvalid elements
     */
    public void readProfileFromFile() throws IOException, JobTitleNotFoundException, ProfileAlreadyExistsException, CSVFormatException {
        String content = new String(file.getBytes(), Charset.defaultCharset());
        CSVFormat format = CSVFormat.EXCEL
                .builder()
                .setHeader()
                .setDelimiter(';')
                .setSkipHeaderRecord(true)
                .build();
        int lineCount = 1;
        try {
            for (CSVRecord record : CSVParser.parse(content, format)) {
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
                        record.get("Geburtsdatum")
                );
                validate(inDTO, lineCount);
                Profile profile = profileService.createProfile(inDTO,
                        userService.getUserByUsername(inDTO.email()));
                lineCount++;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new CSVFormatException(e.getMessage());
        }
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

}