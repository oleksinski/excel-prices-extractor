package com.wteam.horeca;

import com.wteam.horeca.services.ExcelExtractorService;
import com.wteam.horeca.services.ExcelLaunchService;
import com.wteam.horeca.services.ExcelWriteService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * http://poi.apache.org/spreadsheet/quick-guide.html
 * https://habrahabr.ru/company/luxoft/blog/270383/ - Java Stream API examples
 * https://javanerd.ru/основы-java/функциональные-интерфейсы-в-java-8/
 */
@Slf4j
public class ExcelPricesExtractor {

    private final ExcelExtractorService excelExtractorService;
    private final ExcelWriteService excelWriteService;
    private final ExcelLaunchService excelLaunchService;

    public ExcelPricesExtractor(ExcelExtractorService excelExtractorService, ExcelWriteService excelWriteService, ExcelLaunchService excelLaunchService) {
        this.excelExtractorService = excelExtractorService;
        this.excelWriteService = excelWriteService;
        this.excelLaunchService = excelLaunchService;
    }

    public void start(String... args) throws IOException {

    }
}
