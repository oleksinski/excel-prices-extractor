package com.wteam.horeca.services;

import com.wteam.horeca.domain.ExcelSheet;
import com.wteam.horeca.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;

@Service
@Slf4j
public class ExcelExtractorService {

    public ExcelExtractorService() {

    }

    public ExcelSheet extract(String dirPath, String[] searchStrings) {
        File[] files = Utils.getFilesInDirectory(dirPath, ".xls", ".xlsx");
        return extract(files, searchStrings);
    }

    public ExcelSheet extract(String[] filePaths, String[] searchStrings) {
        File[] files = Arrays.stream(filePaths).toArray(File[]::new);
        return extract(files, searchStrings);
    }

    public ExcelSheet extract(File[] files, String[] searchStrings) {
        return null;
    }
}
