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
import java.util.List;
import java.util.stream.Collectors;


public class ExcelGenerator {
    private final List<ProfileDetailsOutDTO> profileList;
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

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
        createCell(row, 0, "Email", style);
        createCell(row, 1, "Telefonnummer", style);
        createCell(row, 2, "JobTitel", style);
        createCell(row, 3, "Abschluss", style);
        createCell(row, 4, "Primaerkompetenz", style);
        createCell(row, 5, "Referenzen", style);
        createCell(row, 6, "Skills", style);
        createCell(row, 7, "Vorname", style);
        createCell(row, 8, "Nachname", style);
        createCell(row, 9, "Geburtsdatum", style);
        createCell(row, 10, "Alter", style);
        return workbook;
    }

    /**
     * Writes data into excel workbook from ProfileDetailsOutDTO List
     * @return Workbook for testing purposes
     */
    public Workbook writeContent() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        style.setBorderRight(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderBottom(BorderStyle.DASHED);
        for (ProfileDetailsOutDTO profile : profileList) {
            Row row = sheet.createRow(rowCount);
            rowCount += 1;

            createCell(row, 0, profile.email(), style);
            createCell(row, 1, profile.phoneNumber(), style);
            createCell(row, 2, profile.jobTitle(), style);
            createCell(row, 3, profile.degree(), style);
            createCell(row, 4, profile.primaryExpertise(), style);
            createCell(row, 5, profile.referenceText(), style);
            createCell(row, 6, profile.skills(), style);
            createCell(row, 7, profile.firstName(), style);
            createCell(row, 8, profile.lastName(), style);
            createCell(row, 9, profile.birthDate(), style);
            createCell(row, 10, profile.age(), style);


        }
        for (int i = 0; i < 10; i++) {
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
