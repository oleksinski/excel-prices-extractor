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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ExcelPricesExtractorConfiguration.class})
public class ExcelWriteServiceTest {

    @Autowired
    private ExcelWriteService excelWriteService;

    @Test
    public void shouldCreateWorkbook() {
        // Test data
        ExcelSheetRow excelSheetRow = new ExcelSheetRow();
        excelSheetRow.setId(ExcelColumnHeader.ID.toString());
        excelSheetRow.setBarcode(ExcelColumnHeader.BARCODE.toString());

        ExcelSheet excelSheet = new ExcelSheet();
        excelSheet.addExcelSheetRows(excelSheetRow);

        // Create workbook
        Workbook workbook = excelWriteService.createWorkbook(excelSheet);

        // Verify excel data
        Sheet sheet = workbook.getSheet(ExcelSheet.SHEET_NAME);
        Row row = sheet.getRow(0);

        // verify cell A
        Cell aCell = row.getCell(Utils.getExcelColumnIndexByLetter("A"));
        Assert.assertEquals(ExcelColumnHeader.ID.toString(), aCell.getStringCellValue());

        // verify cell B
        Cell bCell = row.getCell(Utils.getExcelColumnIndexByLetter("B"));
        Assert.assertEquals(ExcelColumnHeader.BARCODE.toString(), bCell.getStringCellValue());

        // get header cell style
        XSSFCellStyle cellStyle = (XSSFCellStyle) ExcelWriteService.getHeaderCellStyle(sheet);

        // verify cell A style
        XSSFCellStyle aCellStyle = (XSSFCellStyle) aCell.getCellStyle();
        Assert.assertEquals(cellStyle.getAlignmentEnum(), aCellStyle.getAlignmentEnum());
        Assert.assertEquals(cellStyle.getFont().getBold(), aCellStyle.getFont().getBold());
        Assert.assertEquals(cellStyle.getFont().getFontHeightInPoints(), aCellStyle.getFont().getFontHeightInPoints());
        Assert.assertEquals(cellStyle.getFont().getFontName(), aCellStyle.getFont().getFontName());
    }
}