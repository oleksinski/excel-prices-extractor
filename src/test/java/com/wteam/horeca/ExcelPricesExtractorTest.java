package com.wteam.horeca;

import com.wteam.horeca.configuration.ExcelPricesExtractorConfiguration;
import com.wteam.horeca.domain.ExcelSheet;
import com.wteam.horeca.domain.ExcelSheetRow;
import com.wteam.horeca.services.ExcelExtractorService;
import com.wteam.horeca.services.ExcelLaunchService;
import com.wteam.horeca.services.ExcelWriteService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
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

    @Autowired
    private ExcelExtractorService excelExtractorService;

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
        //File file = File.createTempFile("workbook", ".xlsx");
        File file = new File(BASE_PATH + "/out/workbook.xlsx");

        // Create workbook
        Workbook workbook = excelWriteService.createWorkbook(excelSheet);

        // Write workbook to file
        excelWriteService.writeToFile(workbook, file);

        // Open file
        excelLaunchService.execute(file);
    }

    @Test
    @Ignore
    public void shouldTestExtractorService() {
        String dirPath = BASE_PATH + "/test/resources/prices/";
        String[] searchStrings = new String[]{};
        ExcelSheet excelSheet = excelExtractorService.extract(dirPath, searchStrings);
    }

    @Test
    @Ignore
    public void test() throws IOException, InvalidFormatException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("prices/Almaterra.xlsx").getFile());
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Assert.assertEquals("", "");
    }
}
