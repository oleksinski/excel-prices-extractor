package com.wteam.horeca.configuration;

import com.wteam.horeca.domain.DistributorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistributorsConfiguration {

    @Bean
    public DistributorsProperties distributorsProperties() {
        return new DistributorsProperties();
    }
}
