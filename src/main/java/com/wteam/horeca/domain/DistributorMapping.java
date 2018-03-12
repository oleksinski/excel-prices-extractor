package com.wteam.horeca.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorMapping {
    private File file;
    private Workbook workbook;
    private DistributorProperties distributorProperties;
}
