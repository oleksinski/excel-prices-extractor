package com.wteam.horeca;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ExcelPricesExtractorTest {

    @Test
    public void test() throws IOException, InvalidFormatException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("prices/Almaterra.xlsx").getFile());
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Assert.assertEquals("", "");
    }
}
