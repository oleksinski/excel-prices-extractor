package com.wteam.horeca.services;

import com.wteam.horeca.configuration.ExcelPricesExtractorConfiguration;
import com.wteam.horeca.domain.ExcelColumnHeader;
import com.wteam.horeca.domain.ExcelSheet;
import com.wteam.horeca.domain.ExcelSheetRow;
import com.wteam.horeca.utils.Utils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ExcelPricesExtractorConfiguration.class})
public class ExcelWriteServiceTest {

    private final static String ID = "12345";
    private final static String BARCODE = "09876";

    @Autowired
    private ExcelWriteService excelWriteService;

    private Workbook workbook;

    @Before
    public void init() {
        ExcelSheetRow excelSheetRow = new ExcelSheetRow();
        excelSheetRow.setId(ID);
        excelSheetRow.setBarcode(BARCODE);

        ExcelSheet excelSheet = new ExcelSheet();
        excelSheet.addExcelSheetRows(excelSheetRow);

        // Create workbook
        workbook = excelWriteService.createWorkbook(excelSheet);
    }

    @Test
    public void shouldVerifyCreateWorkbookHeaderRow() {


        // Verify excel data
        Sheet sheet = workbook.getSheet(ExcelSheet.SHEET_NAME);

        // verify header row
        Row row = sheet.getRow(0);

        // verify cell A
        Cell aCell = row.getCell(Utils.getExcelColumnIndexByLetter("A"));
        Assert.assertEquals(ExcelColumnHeader.ID.toString(), aCell.getStringCellValue());

        // verify cell B
        Cell bCell = row.getCell(Utils.getExcelColumnIndexByLetter("B"));
        Assert.assertEquals(ExcelColumnHeader.BARCODE.toString(), bCell.getStringCellValue());

        // assert cell styles
        XSSFCellStyle cellStyle = (XSSFCellStyle) ExcelWriteService.getHeaderCellStyle(sheet);
        XSSFCellStyle aCellStyle = (XSSFCellStyle) aCell.getCellStyle();
        assertCellStyles(cellStyle, aCellStyle);
    }

    @Test
    public void shouldVerifyCreateWorkbookContentRow() {

        // Verify excel data
        Sheet sheet = workbook.getSheet(ExcelSheet.SHEET_NAME);

        // verify header row
        Row row = sheet.getRow(1);

        // verify cell A
        Cell aCell = row.getCell(Utils.getExcelColumnIndexByLetter("A"));
        Assert.assertEquals(ID, aCell.getStringCellValue());

        // verify cell B
        Cell bCell = row.getCell(Utils.getExcelColumnIndexByLetter("B"));
        Assert.assertEquals(BARCODE, bCell.getStringCellValue());

        // assert cell styles
        XSSFCellStyle cellStyle = (XSSFCellStyle) ExcelWriteService.getContentCellStyle(sheet);
        XSSFCellStyle aCellStyle = (XSSFCellStyle) aCell.getCellStyle();
        assertCellStyles(cellStyle, aCellStyle);
    }

    private void assertCellStyles(XSSFCellStyle expectedCellStyle, XSSFCellStyle actualCellStyle) {
        Assert.assertEquals(expectedCellStyle.getAlignmentEnum(), actualCellStyle.getAlignmentEnum());
        Assert.assertEquals(expectedCellStyle.getFont().getBold(), actualCellStyle.getFont().getBold());
        Assert.assertEquals(expectedCellStyle.getFont().getFontHeightInPoints(), actualCellStyle.getFont().getFontHeightInPoints());
        Assert.assertEquals(expectedCellStyle.getFont().getFontName(), actualCellStyle.getFont().getFontName());
        Assert.assertEquals(expectedCellStyle.getFillBackgroundColor(), actualCellStyle.getFillBackgroundColor());
    }
}