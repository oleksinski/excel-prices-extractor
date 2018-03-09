package com.wteam.horeca.services;

import com.google.common.base.Strings;
import com.wteam.horeca.domain.DistributorProperties;
import com.wteam.horeca.domain.ExcelSheetRow;
import com.wteam.horeca.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExcelExtractorService {

    public List<ExcelSheetRow> extract(Map<Workbook, DistributorProperties> map, List<String> searchItems) {
        List<ExcelSheetRow> result = new ArrayList<>();

        for (Map.Entry<Workbook, DistributorProperties> entry : map.entrySet()) {
            Workbook workbook = entry.getKey();
            DistributorProperties properties = entry.getValue();

            result.addAll(extract(workbook, properties, searchItems));
        }

        return result;
    }

    public List<ExcelSheetRow> extract(Workbook workbook, DistributorProperties properties, List<String> searchItems) {
        List<ExcelSheetRow> result = new ArrayList<>();

        String SheetTitle = properties.getSheetTitle();
        String distributorName = properties.getDisplayName();
        int rowNumber = properties.getFirstContentRow();
        ExcelSheetRow columnSpecification = properties.getColumnSpecification();

        Sheet sheet = workbook.getSheet(SheetTitle);

        for (int i = rowNumber; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            ExcelSheetRow excelSheetRow = new ExcelSheetRow();
            excelSheetRow.setDistributor(distributorName);

            excelSheetRow.setId(extractCellValue(row, columnSpecification.getId()));

            String id = columnSpecification.getId();
            if (!Strings.isNullOrEmpty(id)) {
                extractCellValue(row, Utils.getExcelColumnIndexByLetter(id));
            }
        }


        return result;
    }

    private String extractCellValue(Row row, String column) {
        return extractCellValue(row, Utils.getExcelColumnIndexByLetter(column));
    }

    private String extractCellValue(Row row, int column) {
        Cell cell = row.getCell(column);
        String value = "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date b = cell.getDateCellValue();
                    if (b != null) {
                        value = b.toString();
                    }
                } else {
                    double n = cell.getNumericCellValue();
                    value = String.valueOf(n);
                }
                break;
            case BOOLEAN:
                boolean b = cell.getBooleanCellValue();
                value = String.valueOf(b);
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
        }

        return value;
    }
}
