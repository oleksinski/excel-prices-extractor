package com.wteam.horeca;

import com.wteam.horeca.domain.ExcelColumnHeader;
import com.wteam.horeca.domain.ExcelSheet;
import com.wteam.horeca.domain.ExcelSheetRow;
import com.wteam.horeca.services.ExcelLaunchService;
import com.wteam.horeca.services.ExcelWriteService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * http://poi.apache.org/spreadsheet/quick-guide.html
 * https://habrahabr.ru/company/luxoft/blog/270383/ - Шпаргалка Java программиста 4. Java Stream API
 * https://javanerd.ru/основы-java/функциональные-интерфейсы-в-java-8/
 */
@Slf4j
public class ExcelPricesExtractor {

    private final ExcelWriteService excelWriteService;
    private final ExcelLaunchService excelLaunchService;

    public ExcelPricesExtractor(ExcelWriteService excelWriteService, ExcelLaunchService excelLaunchService) {
        this.excelWriteService = excelWriteService;
        this.excelLaunchService = excelLaunchService;
    }

    public void start(String... args) throws IOException {

        // Test data
        ExcelSheet excelSheet = new ExcelSheet();
        List<ExcelSheetRow> excelSheetRows = excelSheet.getExcelSheetRows();
        ExcelSheetRow excelSheetRow = new ExcelSheetRow();
        excelSheetRow.setId(ExcelColumnHeader.ID.toString());
        excelSheetRow.setBarcode(ExcelColumnHeader.BARCODE.toString());
        excelSheetRows.add(excelSheetRow);

        // Define output file
        //File file = File.createTempFile("workbook", ".xlsx");
        File file = new File("/Users/olka/Projects/excel-prices-parser/out/workbook.xlsx");
        log.debug(file.getAbsolutePath());

        // Write file
        excelWriteService.write(excelSheetRows, file);

        // Open file
        excelLaunchService.execute(file);
    }
}
