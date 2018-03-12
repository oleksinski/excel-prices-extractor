package com.wteam.horeca;

import com.wteam.horeca.configuration.SpringBootConsoleApplicationConfiguration;
import com.wteam.horeca.services.locale.MessageSourceService;
import com.wteam.horeca.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@Import({SpringBootConsoleApplicationConfiguration.class})
public class SpringBootConsoleApplication implements ApplicationRunner {

    private final static String ARG_DIR = "dir";
    private final static String ARG_FILE = "file";
    private final static String ARG_SEARCH = "search";
    private final static String ARG_OUT = "output";

    @Autowired
    private ExcelPricesExtractor excelPricesExtractor;

    @Autowired
    private MessageSourceService messageSource;

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(SpringBootConsoleApplication.class)
                .headless(false) // Turn headless mode off to run locally installed Excel application
                .run(args);
    }

    /**
     * @param args: --dir="/prices/"
     *              --file="/prices/Prowine.xlsx"
     *              [--output="/tmp/workbook.xlsx"]
     *              --search="bla-bla"
     *              --search="tuk-tuk"
     * @throws Exception
     */
    public void run(ApplicationArguments args) throws Exception {

        File[] input = null;
        File output = null;
        String dir = null;
        String file = null;

        if (args.containsOption(ARG_DIR)) {
            dir = args.getOptionValues(ARG_DIR).get(0);
            input = Utils.getFilesInDirectory(dir, ".xls", ".xlsx");
        } else if (args.containsOption(ARG_FILE)) {
            file = args.getOptionValues(ARG_FILE).get(0);
            input = new File[]{new File(file)};
        }

        if (input == null || input.length == 0) {
            if (dir != null) {
                printUsageExample(ARG_DIR);
            } else if (file != null) {
                printUsageExample(ARG_FILE);
            } else {
                printUsageExample(null);
            }
            return;
        }

        List<String> searchItems = args.getOptionValues(ARG_SEARCH);

        if (searchItems == null || searchItems.isEmpty()) {
            printUsageExample(ARG_SEARCH);
            return;
        }

        if (args.containsOption(ARG_OUT)) {
            output = new File(args.getOptionValues(ARG_OUT).get(0));
        } else {
            output = File.createTempFile("workbook", ".xlsx");
        }

        //ExcelPricesExtractor excelPricesExtractor = applicationContext.getBean(ExcelPricesExtractor.class);
        excelPricesExtractor.start(input, output, searchItems);

        printIgnoredFiles(excelPricesExtractor.getFilesIgnored());
    }

    private void printUsageExample(String argSource) {
        Map<String, String> usage = new HashMap<>();
        usage.put(ARG_DIR, messageSource.getMessage("arg.dir", new Object[]{ARG_DIR}));
        usage.put(ARG_FILE, messageSource.getMessage("arg.file", new Object[]{ARG_FILE}));
        usage.put(ARG_OUT, messageSource.getMessage("arg.out", new Object[]{ARG_OUT}));
        usage.put(ARG_SEARCH, messageSource.getMessage("arg.search", new Object[]{ARG_SEARCH}));

        if (!Objects.isNull(argSource)) {
            out(usage.get(argSource));
            out("---");
        }

        for (Map.Entry<String, String> entry : usage.entrySet()) {
            String param = entry.getKey();
            String message = entry.getValue();

            if (!param.equals(argSource)) {
                out(message);
            }
        }
    }

    private void printIgnoredFiles(Set<File> filesIgnored) {
        List<String> fileList = filesIgnored.stream().map(File::getAbsolutePath).collect(Collectors.toList());
        if (!fileList.isEmpty()) {
            out("Ignored files:");
            for (String file : fileList) {
                out(file);
            }
        }
    }

    private void out(String message) {
        if (!Objects.isNull(message)) {
            System.out.println(message);
        }
    }
}