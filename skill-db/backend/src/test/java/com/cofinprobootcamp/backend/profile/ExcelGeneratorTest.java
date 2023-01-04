package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelGeneratorTest {

    @Test
    void writeHeader() throws IOException {
        ExcelGenerator excelGenerator = new ExcelGenerator(null);
        FileOutputStream fileOutputStream = new FileOutputStream(
                Path.of(Files.currentFolder().toString(), "test.xlsx")
                        .toFile());
        Workbook workbook = excelGenerator.writeHeader();
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
    }
    @Test
    void writeContent() throws IOException, IllegalAccessException {
        ExcelGenerator excelGenerator = new ExcelGenerator(
                List.of(
                        new ProfileDetailsOutDTO(
                                "12345",
                                "1",
                                "2",
                                "job",
                                "expertise",
                                "m.sc.",
                                "aojdnapos",
                                List.of("skill1", "skill2"),
                                "firstname",
                                "lastname",
                                "date",
                                123
                        )
                )
        );
        FileOutputStream fileOutputStream = new FileOutputStream(
                Path.of(Files.currentFolder().toString(), "test.xlsx")
                        .toFile());
        Workbook workbook = excelGenerator.writeContent();
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
    }
}
