package com.wteam.horeca.utils;

import com.wteam.horeca.domain.ExcelCellReference;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public class UtilsTest {

    private Resource pricesTestDirectory = new ClassPathResource("/prices");

    @Test
    public void shouldGetExcelColumnIndexByLetter() {
        Assert.assertEquals(0, Utils.getExcelColumnIndexByLetter("A"));
        Assert.assertEquals(1, Utils.getExcelColumnIndexByLetter("B"));
        Assert.assertEquals(25, Utils.getExcelColumnIndexByLetter("Z"));
        Assert.assertEquals(26, Utils.getExcelColumnIndexByLetter("AA"));
        Assert.assertEquals(27, Utils.getExcelColumnIndexByLetter("AB"));
    }

    @Test
    public void shouldGetFilesInDirectoryWithXlsExtensions() throws IOException {
        File[] files = Utils.getFilesInDirectory(pricesTestDirectory.getFile().getAbsolutePath(), ".xls");

        Assert.assertEquals(1, files.length);
        Assert.assertEquals("Winemart.xls", files[0].getName());
    }

    @Test
    public void shouldGetFilesInDirectoryWithXlsxExtensions() throws IOException {
        File[] files = Utils.getFilesInDirectory(pricesTestDirectory.getFile().getAbsolutePath(), ".xlsx");

        Assert.assertEquals(4, files.length);
        Assert.assertEquals("Almaterra.xlsx", files[0].getName());
        Assert.assertEquals("Bayadera.xlsx", files[1].getName());
        Assert.assertEquals("Prowine.xlsx", files[2].getName());
        Assert.assertEquals("Rishbur.xlsx", files[3].getName());
    }

    @Test
    public void shouldGetFilesInDirectoryWithMultipleExtensions() throws IOException {
        File[] files = Utils.getFilesInDirectory(pricesTestDirectory.getFile().getAbsolutePath(), ".xls", ".xlsx");

        Assert.assertEquals(5, files.length);
        Assert.assertEquals("Almaterra.xlsx", files[0].getName());
        Assert.assertEquals("Bayadera.xlsx", files[1].getName());
        Assert.assertEquals("Prowine.xlsx", files[2].getName());
        Assert.assertEquals("Rishbur.xlsx", files[3].getName());
        Assert.assertEquals("Winemart.xls", files[4].getName());
    }

    @Test
    public void shouldGetExcelCellReference() {
        Assert.assertEquals(new ExcelCellReference("A", 1), Utils.getExcelCellReference("A1"));
        Assert.assertEquals(new ExcelCellReference("AB", 12), Utils.getExcelCellReference("AB12"));
    }
}