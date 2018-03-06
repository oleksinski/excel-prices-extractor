package com.wteam.horeca.domain;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class DistributorProperties {

    private String displayName;

    private String sheetTitle;

    private List<DistributorMarker> markers = new ArrayList<>();

    @Min(1)
    private int firstContentRow = 0;

    private String firstContentColumn = "A";

    private ExcelSheetRow columnSpecification;
}
