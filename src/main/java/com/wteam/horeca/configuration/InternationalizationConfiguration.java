package com.wteam.horeca.configuration;

import com.wteam.horeca.services.locale.MessageSourceService;
import com.wteam.horeca.services.locale.MessageSourceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://www.logicbig.com/tutorials/spring-framework/spring-core/message-sources.html
 */
@Configuration
public class InternationalizationConfiguration {

    @Bean
    public MessageSourceService messageSource () {
        return new MessageSourceServiceImpl();
    }
}
