package com.wteam.horeca.services;

import com.google.common.base.Strings;
import com.google.common.primitives.Booleans;
import com.wteam.horeca.domain.DistributorMapping;
import com.wteam.horeca.domain.DistributorProperties;
import com.wteam.horeca.domain.ExcelColumnHeader;
import com.wteam.horeca.domain.ExcelSheetRow;
import com.wteam.horeca.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ExcelExtractorService {

    public List<ExcelSheetRow> extract(List<DistributorMapping> distributorMappings, List<String> searchItems) {
        List<ExcelSheetRow> result = new ArrayList<>();

        for (DistributorMapping distributorMapping : distributorMappings) {
            result.addAll(extract(
                    distributorMapping.getWorkbook(),
                    distributorMapping.getDistributorProperties(),
                    searchItems));
        }

        return result;
    }

    public List<ExcelSheetRow> extract(Workbook workbook, DistributorProperties properties, List<String> searchItems) {
        List<ExcelSheetRow> result = new ArrayList<>();

        String distributorName = properties.getDisplayName();
        ExcelSheetRow columnSpecification = properties.getColumnSpecification();
        int rowNumber = properties.getFirstContentRow();
        if (rowNumber > 0) {
            rowNumber--;
        }

        Sheet sheet = Utils.getSheet(workbook, properties.getSheetTitle(), properties.getSheetIndex());

        for (int i = rowNumber; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            ExcelSheetRow excelSheetRow = new ExcelSheetRow();

            excelSheetRow.setDistributor(distributorName);

            excelSheetRow.setId(extractCellValue(row, columnSpecification.getId()));
            excelSheetRow.setBarcode(extractCellValue(row, columnSpecification.getBarcode()));
            excelSheetRow.setName(extractCellValue(row, columnSpecification.getName()));
            excelSheetRow.setDescription(extractCellValue(row, columnSpecification.getDescription()));
            excelSheetRow.setVendor(extractCellValue(row, columnSpecification.getVendor()));
            excelSheetRow.setCountry(extractCellValue(row, columnSpecification.getCountry()));
            excelSheetRow.setRegion(extractCellValue(row, columnSpecification.getRegion()));
            excelSheetRow.setCellar(extractCellValue(row, columnSpecification.getCellar()));
            excelSheetRow.setYear(extractCellValue(row, columnSpecification.getYear()));
            excelSheetRow.setDate(extractCellValue(row, columnSpecification.getDate()));
            excelSheetRow.setColor(extractCellValue(row, columnSpecification.getColor()));
            excelSheetRow.setSweetness(extractCellValue(row, columnSpecification.getSweetness()));
            excelSheetRow.setSoilType(extractCellValue(row, columnSpecification.getSoilType()));
            excelSheetRow.setVintage(extractCellValue(row, columnSpecification.getVintage()));
            excelSheetRow.setRanking(extractCellValue(row, columnSpecification.getRanking()));
            excelSheetRow.setGrapeType(extractCellValue(row, columnSpecification.getGrapeType()));
            excelSheetRow.setCapacity(extractCellValue(row, columnSpecification.getCapacity()));
            excelSheetRow.setAlcohol(extractCellValue(row, columnSpecification.getAlcohol()));
            excelSheetRow.setKlass(extractCellValue(row, columnSpecification.getKlass()));
            excelSheetRow.setStock(extractCellValue(row, columnSpecification.getStock()));
            excelSheetRow.setPrice(extractCellValue(row, columnSpecification.getPrice()));

            // perform search
            boolean[] matched = new boolean[searchItems.size()];

            ExcelColumnHeader[] excelColumnHeaders = ExcelColumnHeader.values();

            for (int j = 0; j < searchItems.size(); j++) {
                String searchItem = searchItems.get(j);

                for (ExcelColumnHeader excelColumnHeader : excelColumnHeaders) {
                    String cellData = Utils.getColumnDataByColumnId(excelSheetRow, excelColumnHeader);
                    if (matched(cellData, searchItem)) {
                        matched[j] = true;
                        break;
                    }
                }
            }

            if (Booleans.asList(matched).stream().allMatch(b -> b)) {
                result.add(excelSheetRow);
            }
        }

        return result;
    }

    private boolean matched(String string, String searchItem) {
        return string != null && string.toLowerCase().contains(searchItem.toLowerCase());
    }

    private String extractCellValue(Row row, String column) {
        if (!Strings.isNullOrEmpty(column)) {
            return extractCellValue(row, Utils.getExcelColumnIndexByLetter(column));
        } else {
            return "";
        }
    }

    private String extractCellValue(Row row, int column) {
        String value = "";
        if (column >= 0) {
            Cell cell = row.getCell(column);
            if (!Objects.isNull(cell)) {
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
            }
        }
        return value;
    }
}
