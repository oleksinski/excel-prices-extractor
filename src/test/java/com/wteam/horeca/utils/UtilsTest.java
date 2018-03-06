package com.wteam.horeca.utils;

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
        Assert.assertEquals("PriceRaise.xls", files[0].getName());
    }

    @Test
    public void shouldGetFilesInDirectoryWithXlsxExtensions() throws IOException {
        File[] files = Utils.getFilesInDirectory(pricesTestDirectory.getFile().getAbsolutePath(), ".xlsx");

        Assert.assertEquals(4, files.length);
        Assert.assertEquals("Almaterra.xlsx", files[0].getName());
        Assert.assertEquals("BayaderaWine.xlsx", files[1].getName());
        Assert.assertEquals("Prowine.xlsx", files[2].getName());
        Assert.assertEquals("RishburJan2018.xlsx", files[3].getName());
    }

    @Test
    public void shouldGetFilesInDirectoryWithMultipleExtensions() throws IOException {
        File[] files = Utils.getFilesInDirectory(pricesTestDirectory.getFile().getAbsolutePath(), ".xls", ".xlsx");

        Assert.assertEquals(5, files.length);
        Assert.assertEquals("Almaterra.xlsx", files[0].getName());
        Assert.assertEquals("BayaderaWine.xlsx", files[1].getName());
        Assert.assertEquals("PriceRaise.xls", files[2].getName());
        Assert.assertEquals("Prowine.xlsx", files[3].getName());
        Assert.assertEquals("RishburJan2018.xlsx", files[4].getName());
    }
}