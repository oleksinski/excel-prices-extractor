package com.wteam.horeca.configuration;

import com.wteam.horeca.ExcelPricesExtractor;
import com.wteam.horeca.services.ExcelExtractorService;
import com.wteam.horeca.services.ExcelLaunchService;
import com.wteam.horeca.services.ExcelWriteService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ExcelPricesExtractorConfiguration.class})
public class SpringBootConsoleApplicationConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

    }

    @Bean
    public ExcelPricesExtractor application(ExcelExtractorService excelExtractorService, ExcelWriteService excelWriteService, ExcelLaunchService excelLaunchService) {
        return new ExcelPricesExtractor(excelExtractorService, excelWriteService, excelLaunchService);
    }
}