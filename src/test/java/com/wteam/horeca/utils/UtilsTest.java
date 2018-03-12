package com.wteam.horeca.utils;

import com.wteam.horeca.domain.ExcelCellReference;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        assertXlsFies(files);
    }

    @Test
    public void shouldGetFilesInDirectoryWithXlsxExtensions() throws IOException {
        File[] files = Utils.getFilesInDirectory(pricesTestDirectory.getFile().getAbsolutePath(), ".xlsx");

        Assert.assertEquals(6, files.length);
        assertXlsxFiles(files);
    }

    @Test
    public void shouldGetFilesInDirectoryWithMultipleExtensions() throws IOException {
        File[] files = Utils.getFilesInDirectory(pricesTestDirectory.getFile().getAbsolutePath(), ".xls", ".xlsx");

        Assert.assertEquals(7, files.length);
        assertXlsxFiles(files);
        assertXlsFies(files);
    }

    @Test
    public void shouldGetExcelCellReference() {
        Assert.assertEquals(new ExcelCellReference("A", 1), Utils.getExcelCellReference("A1"));
        Assert.assertEquals(new ExcelCellReference("AB", 12), Utils.getExcelCellReference("AB12"));
    }

    private void assertXlsxFiles(File[] files) {
        List<String> fileNames = Arrays.stream(files).map(File::getName).collect(Collectors.toList());
        Assert.assertTrue(fileNames.contains("Almaterra.xlsx"));
        Assert.assertTrue(fileNames.contains("Bayadera.xlsx"));
        Assert.assertTrue(fileNames.contains("Prowine.xlsx"));
        Assert.assertTrue(fileNames.contains("Rishbur.xlsx"));
        Assert.assertTrue(fileNames.contains("WinebureauRegular.xlsx"));
        Assert.assertTrue(fileNames.contains("WinebureauStrong.xlsx"));
    }

    private void assertXlsFies(File[] files) {
        List<String> fileNames = Arrays.stream(files).map(File::getName).collect(Collectors.toList());
        Assert.assertTrue(fileNames.contains("Winemart.xls"));
    }
}