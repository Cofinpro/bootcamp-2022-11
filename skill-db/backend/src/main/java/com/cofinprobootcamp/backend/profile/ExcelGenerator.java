package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Arrays;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class ExcelGenerator {
    private List<ProfileDetailsOutDTO> profileList;
    private Workbook workbook;
    private Sheet sheet;

    public ExcelGenerator(List<ProfileDetailsOutDTO> profileList) {
        this.profileList = profileList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Profile");
    }

    private void writeHeader() {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 20);
        style.setFont(font);
        List<String> fieldNames = Arrays.stream(
                        ProfileDetailsOutDTO.class
                                .getFields()
                )
                .map(field -> field.getName())
                .toList();
        int col = 0;
        for (String fieldName : fieldNames) {
            createCell(row, col, fieldName, style);
        }
    }
}
