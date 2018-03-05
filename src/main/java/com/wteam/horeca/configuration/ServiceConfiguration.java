package com.wteam.horeca.configuration;

import com.wteam.horeca.services.ExcelLaunchService;
import com.wteam.horeca.services.ExcelWriteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public ExcelWriteService excelWriteService() {
        return new ExcelWriteService();
    }

    @Bean
    public ExcelLaunchService excelLaunchService() {
        return new ExcelLaunchService();
    }
}
