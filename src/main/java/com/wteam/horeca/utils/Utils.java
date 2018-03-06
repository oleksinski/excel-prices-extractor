package com.wteam.horeca.utils;

import org.apache.poi.hssf.util.CellReference;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class Utils {

    public static int getExcelColumnIndexByLetter(String column) {
        return CellReference.convertColStringToIndex(column);
    }

    public static File[] getFilesInDirectory(String dirPath, String... extensions) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles((d, name) -> Arrays.stream(extensions).anyMatch(name::endsWith));
        if (!Objects.isNull(files)) {
            Arrays.sort(files);
        }
        return files;
    }
}
