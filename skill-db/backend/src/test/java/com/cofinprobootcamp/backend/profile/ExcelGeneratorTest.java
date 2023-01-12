package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ExcelGeneratorTest {

    @Test
    void writeHeader() throws IOException {
        ExcelGenerator excelGenerator = new ExcelGenerator(null);
        File file = Path.of(
                        Files.currentFolder().toString(), "test.xlsx")
                .toFile();
        FileOutputStream fileOutputStream = new FileOutputStream(
                file);
        Workbook workbook = excelGenerator.writeHeader();
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
        assertThat(file.exists()).isTrue();
        file.delete();
    }

    @Test
    void writeContent() throws IOException, IllegalAccessException {
        ExcelGenerator excelGenerator = new ExcelGenerator(
                List.of(
                        testOutProfile()
                )
        );
        File file = Path.of(
                Files.currentFolder().toString(), "test.xlsx")
                .toFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        Workbook workbook = excelGenerator.writeContent();
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
        assertThat(file.exists()).isTrue();
        file.delete();
    }
    @Test
    void createExcel() throws IOException, IllegalAccessException {
        File file = Path.of(
                        Files.currentFolder().toString(), "test.xlsx")
                .toFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ExcelGenerator excelGenerator = new ExcelGenerator(List.of(
                testOutProfile()
        ));
        excelGenerator.createExcel(fileOutputStream);
        assertThat(file.exists()).isTrue();
    }

    private ProfileDetailsOutDTO testOutProfile() {
        return new ProfileDetailsOutDTO(
                "1AFOIJ",
                "lars.testermann@cofinpro.de",
                "21111111",
                "Expert Consultant",
                "M. Sc.",
                "Fach",
                "...",
                List.of("skill1", "skill2"),
                "Vorname",
                "Nachname",
                "1999-09-19",
                123,
                "JNOF1"
        );
    }
}
