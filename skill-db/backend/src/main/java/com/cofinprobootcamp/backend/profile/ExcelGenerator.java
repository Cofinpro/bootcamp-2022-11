package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.profile.dto.ProfileDetailsOutDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ExcelGenerator {
    private List<ProfileDetailsOutDTO> profileList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List<ProfileDetailsOutDTO> profileList) {
        this.profileList = profileList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Profile");
    }

    public Workbook writeHeader() {
        XSSFRow row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        style.setBorderRight(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderBottom(BorderStyle.MEDIUM);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
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

    public Workbook writeContent() throws IllegalAccessException {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        style.setBorderRight(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderBottom(BorderStyle.DASHED);
        int colCount = 0;
        for (ProfileDetailsOutDTO profile : profileList) {
            Row row = sheet.createRow(rowCount);
            rowCount += 1;
            colCount = 0;
            Field[] profileFields = ProfileDetailsOutDTO.class.getDeclaredFields();
            for (Field field : profileFields) {
                field.setAccessible(true);
                createCell(row, colCount, field.get(profile), style);
                colCount++;
            }
        }
        for (int i = 0; i < colCount; i++) {
            sheet.autoSizeColumn(i);
        }
        return workbook;
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        if (valueOfCell instanceof Integer) {
            Cell cell = row.createCell(columnCount, CellType.NUMERIC);
            cell.setCellStyle(style);
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof String) {
            CellUtil.createCell(row,columnCount,(String) valueOfCell, style);
        } else if (valueOfCell instanceof Boolean) {
            Cell cell = row.createCell(columnCount, CellType.BOOLEAN);
            cell.setCellStyle(style);
            cell.setCellValue((Boolean) valueOfCell);
        } else if (valueOfCell instanceof List<?>) {
            Cell cell = row.createCell(columnCount,CellType.STRING);
            cell.setCellStyle(style);
            cell.setCellValue(((List<?>) valueOfCell).stream()
                    .map(n -> String.valueOf(n))
                    .collect(Collectors.joining(",")));
        }
    }

    public void createExcel(OutputStream outputStream)
            throws IllegalAccessException, IOException {
        writeHeader();
        writeContent();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
