package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CSVReader {
    private MultipartFile file;
    public void readProfileFromFile() throws IOException {
        String content = new String(file.getBytes());
        CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
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
            Set<ConstraintViolation<ProfileCreateInDTO>> violations = validator.validate(inDTO);
        }

    }

}
