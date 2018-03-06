package com.wteam.horeca.domain;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;

@Data
@ToString
public class DistributorMarker {

    @Min(1)
    private int row = 0;

    private String column = "A";

    private String text;
}