package com.wteam.horeca.services;

import com.google.common.base.Strings;
import com.wteam.horeca.domain.ExcelColumnHeader;
import com.wteam.horeca.domain.ExcelSheet;
import com.wteam.horeca.domain.ExcelSheetRow;
import com.wteam.horeca.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ExcelWriteService {

    private static void autoSizeColumns(Sheet sheet) {
        if (sheet.getPhysicalNumberOfRows() > 0) {
            Row row = sheet.getRow(0);
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                sheet.autoSizeColumn(columnIndex);
            }
        }
    }

    static String getCellDataOrFallback(ExcelSheetRow data, ExcelColumnHeader excelColumnHeader) {
        String value;
        if (Objects.isNull(data)) {
            value = excelColumnHeader.toString();
        } else {
            value = Utils.getColumnDataByColumnId(data, excelColumnHeader);
        }
        return value;
    }

    static CellStyle getHeaderCellStyle(Sheet sheet) {
        return getCellStyle(sheet, 12, true, HorizontalAlignment.CENTER, IndexedColors.LIGHT_GREEN);
    }

    static CellStyle getContentCellStyle(Sheet sheet) {
        return getCellStyle(sheet, 11, false, HorizontalAlignment.LEFT, null);
    }

    private static CellStyle getCellStyle(Sheet sheet, int fontHeight, boolean bold, HorizontalAlignment alignment, IndexedColors bgColor) {
        Workbook workbook = sheet.getWorkbook();

        CellStyle cellStyle = workbook.createCellStyle();

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) fontHeight);
        font.setBold(bold);

        cellStyle.setFont(font);
        cellStyle.setAlignment(alignment);

        if (bgColor != null) {
            cellStyle.setFillBackgroundColor(bgColor.getIndex());
            cellStyle.setFillPattern(FillPatternType.NO_FILL);
        }

        return cellStyle;
    }

    private static void createCell(Row data, int column, String value, CellStyle cellStyle, ExcelColumnHeader excelColumnHeader) {
        Cell cell = data.createCell(column);

        if (ExcelColumnHeader.PRICE.equals(excelColumnHeader)
                || ExcelColumnHeader.STOCK.equals(excelColumnHeader)
                || ExcelColumnHeader.CAPACITY.equals(excelColumnHeader)
                || ExcelColumnHeader.ALCOHOL.equals(excelColumnHeader)) {
            try {
                cell.setCellValue(Double.parseDouble(value));
            } catch (NumberFormatException e) {
                cell.setCellValue(value);
            }
        } else {
            cell.setCellValue(value);
        }
        cell.setCellStyle(cellStyle);
    }

    public Workbook createWorkbook(ExcelSheet excelSheet) {
        return createWorkbook(excelSheet, ExcelSheet.SHEET_NAME);
    }

    public Workbook createWorkbook(ExcelSheet excelSheet, String sheetName) {
        return createWorkbook(excelSheet.getExcelSheetRows(), sheetName);
    }

    public Workbook createWorkbook(List<ExcelSheetRow> excelSheetRows, String sheetName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // write header
        writeRow(excelSheetRows, sheet, sheet.createRow(0), null);

        // write data
        int i = 1;
        for (ExcelSheetRow row : excelSheetRows) {
            writeRow(excelSheetRows, sheet, sheet.createRow(i++), row);
        }

        autoSizeColumns(sheet);

        return workbook;
    }

    public void writeToFile(Workbook workbook, String filePath) throws IOException {
        writeToFile(workbook, new File(filePath));
    }

    public void writeToFile(Workbook workbook, File file) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
    }

    private void writeRow(List<ExcelSheetRow> excelSheetRows, Sheet sheet, Row row, ExcelSheetRow data) {

        // format cell style
        CellStyle cellStyle;
        if (Objects.isNull(data)) {
            cellStyle = getHeaderCellStyle(sheet);
            row.setHeightInPoints(30);
        } else {
            cellStyle = getContentCellStyle(sheet);
        }

        int col = 0;

        ExcelColumnHeader[] excelColumnHeaders = ExcelColumnHeader.values();

        for (ExcelColumnHeader excelColumnHeader : excelColumnHeaders) {
            if (excelSheetRows.stream().anyMatch(r -> !Strings.isNullOrEmpty(Utils.getColumnDataByColumnId(r, excelColumnHeader)))) {
                createCell(row, col++, getCellDataOrFallback(data, excelColumnHeader), cellStyle, excelColumnHeader);
            }
        }
    }
}
