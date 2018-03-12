package com.wteam.horeca.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelCellReference {
    private String column = "A";

    @Min(1)
    private int row = 0;
}
