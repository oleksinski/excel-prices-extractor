package com.wteam.horeca.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
public class DistributorProperties {

    private String displayName;

    private String sheetTitle;

    private int sheetIndex;

    private List<DistributorMarker> markers = new ArrayList<>();

    @Min(1)
    private int firstContentRow = 0;

    private ExcelSheetRow columnSpecification;
}
