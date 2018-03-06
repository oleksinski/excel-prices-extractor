package com.wteam.horeca.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DistributorsConfiguration.class,
        ServiceConfiguration.class})
public class ExcelPricesExtractorConfiguration {
}
