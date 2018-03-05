package com.wteam.horeca;

import com.wteam.horeca.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApplicationConfiguration.class})
public class SpringBootConsoleApplication implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringBootConsoleApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(SpringBootConsoleApplication.class)
                .headless(false) // Turn headless mode off to run locally installed Excel application
                .run(args);
    }

    public void run(String... args) throws Exception {
        applicationContext.getBean(ExcelPricesExtractor.class).start(args);
    }
}