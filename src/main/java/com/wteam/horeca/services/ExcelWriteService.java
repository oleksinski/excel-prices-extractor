package com.wteam.horeca.services;

import com.google.common.base.Strings;
import com.wteam.horeca.domain.ExcelColumnHeader;
import com.wteam.horeca.domain.ExcelSheetRow;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ExcelWriteService {

    private final static String SHEET_NAME = "Prices";

    public void write(List<ExcelSheetRow> excelSheetRows, String filePath) throws IOException {
        write(excelSheetRows, new File(filePath));
    }

    public void write(List<ExcelSheetRow> excelSheetRows, File file) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET_NAME);

        //sheet.setColumnWidth(0, 6000);
        //sheet.setColumnWidth(1, 4000);

        // write header
        writeRow(excelSheetRows, sheet, sheet.createRow(0), null);

        // write data
        int i = 0;
        for (ExcelSheetRow row : excelSheetRows) {
            writeRow(excelSheetRows, sheet, sheet.createRow(i++), row);
        }

        // Save file to disk
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
    }

    private void writeRow(List<ExcelSheetRow> excelSheetRows, Sheet sheet, Row row, ExcelSheetRow data) {
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
            if (excelSheetRows.stream().anyMatch(r -> !Strings.isNullOrEmpty(getColumnDataByColumnId(r, excelColumnHeader)))) {
                createCell(row, col++, getCellDataOrFallback(data, excelColumnHeader), cellStyle);
            }
        }
    }

    private static String getColumnDataByColumnId(ExcelSheetRow data, ExcelColumnHeader excelColumnHeader) {
        if (Objects.isNull(data)) {
            return null;
        } else {
            String value;
            switch (excelColumnHeader) {
                case ID:
                    value = data.getId();
                    break;
                case BARCODE:
                    value = data.getBarcode();
                    break;
                case NAME:
                    value = data.getName();
                    break;
                case DESCRIPTION:
                    value = data.getDescription();
                    break;
                case VENDOR:
                    value = data.getVendor();
                    break;
                case COUNTRY:
                    value = data.getCountry();
                    break;
                case REGION:
                    value = data.getRegion();
                    break;
                case CELLAR:
                    value = data.getCellar();
                    break;
                case YEAR:
                    value = data.getYear();
                    break;
                case DATE:
                    value = data.getDate();
                    break;
                case COLOR:
                    value = data.getColor();
                    break;
                case SWEETNESS:
                    value = data.getSweetness();
                    break;
                case SOIL_TYPE:
                    value = data.getSoilType();
                    break;
                case VINTAGE:
                    value = data.getVintage();
                    break;
                case RANKING:
                    value = data.getRanking();
                    break;
                case GRAPE_TYPE:
                    value = data.getGrapeType();
                    break;
                case CAPACITY:
                    value = data.getCapacity();
                    break;
                case ALCOHOL:
                    value = data.getAlcohol();
                    break;
                case KLASS:
                    value = data.getKlass();
                    break;
                case STOCK:
                    value = data.getStock();
                    break;
                case PRICE:
                    value = data.getPrice();
                    break;
                default:
                    value = null;
            }
            return value;
        }
    }

    private static String getCellDataOrFallback(ExcelSheetRow data, ExcelColumnHeader excelColumnHeader) {
        String value;
        if (Objects.isNull(data)) {
            value = excelColumnHeader.toString();
        } else {
            value = getColumnDataByColumnId(data, excelColumnHeader);
        }
        return value;
    }

    private static CellStyle getHeaderCellStyle(Sheet sheet) {
        return getCellStyle(sheet, 12, true, HorizontalAlignment.CENTER);
    }

    private static CellStyle getContentCellStyle(Sheet sheet) {
        return getCellStyle(sheet, 11, false, HorizontalAlignment.CENTER);
    }

    private static CellStyle getCellStyle(Sheet sheet, int fontHeight, boolean bold, HorizontalAlignment alignment) {
        Workbook workbook = sheet.getWorkbook();

        CellStyle cellStyle = workbook.createCellStyle();

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) fontHeight);
        font.setBold(bold);

        cellStyle.setFont(font);
        cellStyle.setAlignment(alignment);

        return cellStyle;
    }

    private static void createCell(Row data, int column, String value, CellStyle cellStyle) {
        Cell cell = data.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }
}
