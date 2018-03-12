package com.wteam.horeca.services;

import com.wteam.horeca.configuration.ExcelPricesExtractorConfiguration;
import com.wteam.horeca.domain.DistributorMapping;
import com.wteam.horeca.utils.Utils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ExcelPricesExtractorConfiguration.class})
public class ExcelDistributorDetectorServiceTest {

    private File[] files;

    @Autowired
    private ExcelDistributorDetectorService excelDistributorDetectorService;

    @Before
    public void init() throws IOException {
        files = Utils.getFilesInDirectory(new ClassPathResource("/prices").getFile().getAbsolutePath(), ".xls", ".xlsx");
    }

    @Test
    @Ignore
    public void shouldDetectConfiguration() throws IOException {
        List<DistributorMapping> distributorMappings = excelDistributorDetectorService.detect(files);
        assertEquals(files.length, distributorMappings.size());

        Set<File> ignoredFileList = excelDistributorDetectorService.getFilesIgnored();
        assertTrue(ignoredFileList.isEmpty());
    }
}