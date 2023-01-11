package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
                        new ProfileDetailsOutDTO(
                                "1AFOIJNOF",
                                "asfeoijpawd",
                                "21111111",
                                "job",
                                "expertise",
                                "m.sc.",
                                "aojdnapos",
                                List.of("skill1", "skill2"),
                                "firstname",
                                "lastname",
                                "date",
                                123,
                                null
                        )
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
                new ProfileDetailsOutDTO(
                        "1AFOIJNOF",
                        "asfeoijpawd",
                        "21111111",
                        "job",
                        "m.sc",
                        "degree",
                        "aojdnapos",
                        List.of("skill1", "skill2"),
                        "firstname",
                        "lastname",
                        "date",
                        123,
                        null
                )
        ));
        excelGenerator.createExcel(fileOutputStream);
        assertThat(file.exists()).isTrue();
    }

}
