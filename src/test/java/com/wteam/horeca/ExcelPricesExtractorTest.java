package com.wteam.horeca;

import com.wteam.horeca.configuration.ExcelPricesExtractorConfiguration;
import com.wteam.horeca.domain.ExcelSheet;
import com.wteam.horeca.domain.ExcelSheetRow;
import com.wteam.horeca.services.ExcelLaunchService;
import com.wteam.horeca.services.ExcelWriteService;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ExcelPricesExtractorConfiguration.class})
public class ExcelPricesExtractorTest {

    private final static String BASE_PATH = "/Users/olka/Projects/excel-prices-extractor";

    @Autowired
    private ExcelWriteService excelWriteService;

    @Autowired
    private ExcelLaunchService excelLaunchService;

    @Test
    @Ignore
    public void shouldLaunchExcelApplication() throws IOException {
        // Test data
        ExcelSheetRow excelSheetRow = new ExcelSheetRow();
        excelSheetRow.setId("25");
        excelSheetRow.setBarcode("12345");

        ExcelSheet excelSheet = new ExcelSheet();
        excelSheet.addExcelSheetRows(excelSheetRow);

        // Define output file
        File file = new File(BASE_PATH + "/target/workbook.xlsx");

        // Create workbook
        Workbook workbook = excelWriteService.createWorkbook(excelSheet);

        // Write workbook to file
        excelWriteService.writeToFile(workbook, file);

        // Open file
        excelLaunchService.execute(file);
    }

}
