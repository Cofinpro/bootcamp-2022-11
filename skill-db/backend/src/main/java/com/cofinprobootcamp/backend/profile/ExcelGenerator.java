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

    /**
     * @param profileList profiles to be exportet to excel
     */
    public ExcelGenerator(List<ProfileDetailsOutDTO> profileList) {
        this.profileList = profileList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Profile");
    }

    /**
     * writes header of excel workbook from ProfiledetailsOutDTO Class
     * @return workbook for testing purposes
     */
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

    /**
     * Writes data into excel workbook from ProfileDetailsOutDTO List
     * @return Workbook for testing purposes
     * @throws IllegalAccessException should never be thrown!
     */
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

    /**
     * Helper function for creation of cells with a fitting celltype.
     * might have to be expanded if new data types are added
     * @param row Row object
     * @param columnCount integer describing col to write in
     * @param valueOfCell value to write
     * @param style style of the cell
     */
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        if (valueOfCell instanceof Integer) {
            //if Over 25000 profiles exists, they will not be displayed correctly because every
            // int cell style will be interpreted as seperate style!
            // (and excel can only handle 25000 styles)
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
            CellUtil.createCell(row,columnCount,
                    ((List<?>) valueOfCell).stream()
                    .map(n -> String.valueOf(n))
                    .collect(Collectors.joining(",")),
                    style);
        }
    }

    /**
     * @param outputStream outputstream to which excel should be written
     * @throws IllegalAccessException should never actually be thrown!
     * @throws IOException if something goes wrong with outputstream!
     */
    public void createExcel(OutputStream outputStream)
            throws IllegalAccessException, IOException {
        writeHeader();
        writeContent();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
