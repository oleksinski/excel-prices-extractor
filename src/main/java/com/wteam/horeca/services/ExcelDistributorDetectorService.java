package com.wteam.horeca.services;

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

    public Map<Workbook, DistributorProperties> detect(File[] files) {

        Map<Workbook, DistributorProperties> result = new HashMap<>();

        List<DistributorProperties> configuration = distributorsProperties.getConfiguration();

        for (File file: files) {

            for (DistributorProperties properties: configuration) {
                try {
                    Workbook wb = WorkbookFactory.create(file);
                    Sheet sheet = wb.getSheet(properties.getSheetTitle());

                    if (Objects.isNull(sheet)) {
                        wb.close();
                        filesIgnored.add(file);
                    } else {
                        List<DistributorMarker> markers = properties.getMarkers();
                        for (DistributorMarker marker: markers) {
                            int rowNumber = marker.getRow();
                            if (rowNumber > 0) {
                                rowNumber--;
                            }
                            Row row = sheet.getRow(rowNumber);
                            Cell cell = row.getCell(Utils.getExcelColumnIndexByLetter(marker.getColumn()));
                            String cellValue = cell.getStringCellValue();
                            String markerValue = marker.getText();
                            if (!cellValue.contains(markerValue)) {
                                wb.close();
                                filesIgnored.add(file);
                                break;
                            } else {
                                filesIgnored.remove(file);
                            }
                        }
                        if (!filesIgnored.contains(file)) {
                            result.put(wb, properties);
                        }
                    }

                } catch (InvalidFormatException | IOException e) {
                    filesIgnored.add(file);
                }
            }
        }

        return result;
    }
}
