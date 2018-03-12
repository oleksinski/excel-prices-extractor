package com.wteam.horeca.services;

import com.wteam.horeca.domain.DistributorMapping;
import com.wteam.horeca.domain.DistributorMarker;
import com.wteam.horeca.domain.DistributorProperties;
import com.wteam.horeca.domain.DistributorsProperties;
import com.wteam.horeca.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ExcelDistributorDetectorService {

    @Autowired
    private DistributorsProperties distributorsProperties;

    private Set<File> filesIgnored = new HashSet<>();

    public Set<File> getFilesIgnored() {
        return filesIgnored;
    }

    public List<DistributorMapping> detect(File[] files) {

        List<DistributorMapping> distributorMappings = new ArrayList<>();

        List<DistributorProperties> configuration = distributorsProperties.getConfiguration();

        for (File file : files) {

            for (DistributorProperties properties : configuration) {
                try {
                    Workbook workbook = WorkbookFactory.create(file);
                    Sheet sheet = Utils.getSheet(workbook, properties.getSheetTitle(), properties.getSheetIndex());

                    if (Objects.isNull(sheet)) {
                        workbook.close();
                        filesIgnored.add(file);
                    } else {
                        List<DistributorMarker> markers = properties.getMarkers();
                        for (DistributorMarker marker : markers) {
                            int rowNumber = marker.getRow();
                            if (rowNumber > 0) {
                                rowNumber--;
                            }
                            Row row = sheet.getRow(rowNumber);
                            Cell cell = row.getCell(Utils.getExcelColumnIndexByLetter(marker.getColumn()));
                            String cellValue = cell.getStringCellValue();
                            String markerValue = marker.getText();
                            if (!cellValue.contains(markerValue)) {
                                workbook.close();
                                filesIgnored.add(file);
                                break;
                            } else {
                                filesIgnored.remove(file);
                            }
                        }
                        if (!filesIgnored.contains(file)) {
                            distributorMappings.add(new DistributorMapping(file, workbook, properties));
                            break;
                        }
                    }

                } catch (InvalidFormatException | IOException e) {
                    filesIgnored.add(file);
                }
            }
        }

        return distributorMappings;
    }
}
