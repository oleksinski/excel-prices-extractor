package com.wteam.horeca.utils;

import org.apache.poi.hssf.util.CellReference;

public class ExcelColumnToIndexTransformer {

    public static int transform(String column) {
        return CellReference.convertColStringToIndex(column);
    }
}
