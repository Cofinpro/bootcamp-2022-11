package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.exceptions.CSVArgumentNotValidException;
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

    public void readProfileFromFile() throws IOException, JobTitleNotFoundException, ProfileAlreadyExistsException {
        String content = new String(file.getBytes());
        CSVFormat format = CSVFormat.EXCEL
                .builder()
                .setHeader()
                .setDelimiter(';')
                .setSkipHeaderRecord(true)
                .build();
        int lineCount = 1;
        for (CSVRecord record : CSVParser.parse(content, format)) {
            ProfileCreateInDTO inDTO = new ProfileCreateInDTO(
                    record.get("Email"),
                    record.get("Vorname"),
                    record.get("Nachname"),
                    record.get("JobTitel"),
                    record.get("Abschluss"),
                    record.get("Primärkompetenz"),
                    record.get("Referenzen"),
                    Arrays.stream(record.get("Skills").split(";")).toList(),
                    record.get("Telefonnummer"),
                    record.get("Geburtsdatum")
            );
            validate(inDTO, lineCount);
            Profile profile = profileService.createProfile(inDTO,
                    userService.getUserByUsername(inDTO.email()));
            lineCount ++;
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
