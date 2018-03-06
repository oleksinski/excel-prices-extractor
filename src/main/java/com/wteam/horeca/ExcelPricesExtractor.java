package com.wteam.horeca;

import com.wteam.horeca.domain.ExcelColumnHeader;
import com.wteam.horeca.domain.ExcelSheet;
import com.wteam.horeca.domain.ExcelSheetRow;
import com.wteam.horeca.services.ExcelExtractorService;
import com.wteam.horeca.services.ExcelLaunchService;
import com.wteam.horeca.services.ExcelWriteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * http://poi.apache.org/spreadsheet/quick-guide.html
 * https://habrahabr.ru/company/luxoft/blog/270383/ - Java Stream API examples
 * https://javanerd.ru/основы-java/функциональные-интерфейсы-в-java-8/
 */
@Slf4j
public class ExcelPricesExtractor {

    private final ExcelExtractorService excelExtractorService;
    private final ExcelWriteService excelWriteService;
    private final ExcelLaunchService excelLaunchService;

    public ExcelPricesExtractor(ExcelExtractorService excelExtractorService, ExcelWriteService excelWriteService, ExcelLaunchService excelLaunchService) {
        this.excelExtractorService = excelExtractorService;
        this.excelWriteService = excelWriteService;
        this.excelLaunchService = excelLaunchService;
    }

    public void start(String... args) throws IOException {

        // Test data
        ExcelSheetRow excelSheetRow = new ExcelSheetRow();
        excelSheetRow.setId(ExcelColumnHeader.ID.toString());
        excelSheetRow.setBarcode(ExcelColumnHeader.BARCODE.toString());

        ExcelSheet excelSheet = new ExcelSheet();
        excelSheet.addExcelSheetRows(excelSheetRow);

        // Define output file
        //File file = File.createTempFile("workbook", ".xlsx");
        File file = new File("/Users/olka/Projects/excel-prices-extractor/out/workbook.xlsx");
        log.debug(file.getAbsolutePath());


//        String dirPath = "/Users/olka/Projects/excel-prices-parser/test/resources/prices/";
//        String[] searchStrings = new String[]{};
//        ExcelSheet _excelSheet = excelExtractorService.extract(dirPath, searchStrings);

        // Create workbook
        Workbook workbook = excelWriteService.createWorkbook(excelSheet);

        // Write workbook to file
        excelWriteService.writeToFile(workbook, file);

        // Open file
        excelLaunchService.execute(file);
    }
}
