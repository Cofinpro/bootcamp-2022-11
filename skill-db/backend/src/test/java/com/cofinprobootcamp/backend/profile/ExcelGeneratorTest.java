package com.cofinprobootcamp.backend.profile;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ExcelGeneratorTest {

    @Test
    void writeHeader() throws IOException {
        ExcelGenerator excelGenerator = new ExcelGenerator(null);
        FileOutputStream fileOutputStream = new FileOutputStream(
                Path.of(Files.currentFolder().toString(), "test.xlsx")
                        .toFile());
        Workbook workbook = excelGenerator.writeHeader(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
    }

    void writeContent() {

    }
}
