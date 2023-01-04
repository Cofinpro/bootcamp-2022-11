package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public class ExcelGenerator {
    private List<ProfileDetailsOutDTO> profileList;
    private Workbook workbook;
    private Sheet sheet;

    public ExcelGenerator(List<ProfileDetailsOutDTO> profileList) {
        this.profileList = profileList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Profile");
    }

    public Workbook writeHeader() {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 20);
        style.setFont(font);
        List<String> fieldNames = Arrays.stream(
                        ProfileDetailsOutDTO.class
                                .getDeclaredFields()
                )
                .map(field -> {
                    field.setAccessible(true);
                    return field.getName();
                })
                .toList();
        int col = 0;
        for (String fieldName : fieldNames) {
            createCell(row, col, fieldName, style);
            col += 1;
        }
        return workbook;
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        if (valueOfCell instanceof Integer) {
            Cell cell = row.createCell(columnCount, CellType.NUMERIC);
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof String) {
            Cell cell = row.createCell(columnCount, CellType.STRING);
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Boolean) {
            Cell cell = row.createCell(columnCount, CellType.BOOLEAN);
            cell.setCellValue((Boolean) valueOfCell);
        }
    }
    public Workbook writeContent() throws IllegalAccessException {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeight((short) 14);
        style.setFont(font);
        for (ProfileDetailsOutDTO profile : profileList) {
            Row row = sheet.createRow(rowCount);
            rowCount += 1;
            int colCount = 0;
            Field[] profileFields = ProfileDetailsOutDTO.class.getDeclaredFields();
            for (Field field : profileFields) {
                field.setAccessible(true);
                createCell();
            }
        }
        return workbook;
    }
}
