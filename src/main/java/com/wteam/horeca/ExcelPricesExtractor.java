package com.wteam.horeca;

import com.wteam.horeca.domain.DistributorProperties;
import com.wteam.horeca.domain.ExcelSheet;
import com.wteam.horeca.domain.ExcelSheetRow;
import com.wteam.horeca.services.ExcelDistributorDetectorService;
import com.wteam.horeca.services.ExcelExtractorService;
import com.wteam.horeca.services.ExcelLaunchService;
import com.wteam.horeca.services.ExcelWriteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * http://poi.apache.org/spreadsheet/quick-guide.html
 * https://habrahabr.ru/company/luxoft/blog/270383/ - Java Stream API examples
 * https://javanerd.ru/основы-java/функциональные-интерфейсы-в-java-8/
 */
@Slf4j
public class ExcelPricesExtractor {

    @Autowired
    private ExcelExtractorService excelExtractorService;

    @Autowired
    private ExcelWriteService excelWriteService;

    @Autowired
    private ExcelLaunchService excelLaunchService;

    @Autowired
    private ExcelDistributorDetectorService excelDistributorDetectorService;

    private Set<File> filesIgnored = new HashSet<>();

    public void start(File[] files, File output, List<String> searchItems) throws IOException {

        Map<Workbook, DistributorProperties> workBookToDistributorPropertiesMap = excelDistributorDetectorService.detect(files);

        filesIgnored = excelDistributorDetectorService.getFilesIgnored();

        List<ExcelSheetRow> excelSheetRows = excelExtractorService.extract(workBookToDistributorPropertiesMap, searchItems);

        Workbook workbook = excelWriteService.createWorkbook(excelSheetRows);

        excelWriteService.writeToFile(workbook, output);

        excelLaunchService.execute(output);
    }

    public Set<File> getFilesIgnored() {
        return filesIgnored;
    }
}
