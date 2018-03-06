package com.wteam.horeca.domain;

import org.junit.Assert;
import org.junit.Test;

public class ExcelColumnHeaderTest {

    @Test
    public void shouldGetIdByName() {
        Assert.assertEquals(ExcelColumnHeader.CAPACITY, ExcelColumnHeader.getIdByName("Capacity"));
        Assert.assertEquals(ExcelColumnHeader.GRAPE_TYPE, ExcelColumnHeader.getIdByName("Grape type"));
    }
}