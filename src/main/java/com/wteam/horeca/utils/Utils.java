package com.wteam.horeca.utils;

import com.google.common.base.Strings;
import com.wteam.horeca.domain.ExcelCellReference;
import com.wteam.horeca.domain.ExcelColumnHeader;
import com.wteam.horeca.domain.ExcelSheetRow;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final Pattern CELL_REFERENCE_PATTERN = Pattern.compile("^([A-Za-z]+)([0-9]+)$");

    public static int getExcelColumnIndexByLetter(String column) {
        return CellReference.convertColStringToIndex(column);
    }

    public static File[] getFilesInDirectory(String dirPath, String... extensions) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles((d, name) -> Arrays.stream(extensions).anyMatch(name::endsWith));
        if (!Objects.isNull(files)) {
            Arrays.sort(files);
        }
        return files;
    }

    public static String getColumnDataByColumnId(ExcelSheetRow data, ExcelColumnHeader excelColumnHeader) {
        if (Objects.isNull(data)) {
            return null;
        } else {
            String value;
            switch (excelColumnHeader) {
                case DISTRIBUTOR:
                    value = data.getDistributor();
                    break;
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

    public static Sheet getSheet(Workbook workbook, String sheetName, int sheetIndex) {
        if (!Strings.isNullOrEmpty(sheetName)) {
            sheetIndex = workbook.getSheetIndex(sheetName);
        }
        if (sheetIndex > -1) {
            return workbook.getSheetAt(sheetIndex);
        } else {
            return null;
        }
    }

    public static ExcelCellReference getExcelCellReference(String stringCellReference) {
        ExcelCellReference excelCellReference = null;

        Matcher matcher = CELL_REFERENCE_PATTERN.matcher(stringCellReference);
        if (matcher.find()) {
            String col = matcher.group(1);
            int row = Integer.valueOf(matcher.group(2));

            excelCellReference = new ExcelCellReference(col, row);
        }

        return excelCellReference;
    }
}
