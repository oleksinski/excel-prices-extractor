package com.wteam.horeca.domain;

import com.google.common.base.Strings;
import com.wteam.horeca.utils.Utils;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

@Data
public class DistributorMarker {

    /**
     * Format: "A1:text"
     */
    private String marker;

    @Getter
    private ExcelCellReference excelCellReference;

    @Getter
    private String text;

    public void setMarker(String marker) {
        this.marker = marker;

        if (!Strings.isNullOrEmpty(marker)) {
            String[] markers = marker.split(":", 2);
            if (markers.length == 2) {
                this.excelCellReference = Utils.getExcelCellReference(markers[0]);
                this.text = markers[1];
            }
        }
    }

    public String getColumn() {
        return Objects.isNull(excelCellReference) ? "A" : excelCellReference.getColumn();
    }

    public int getRow() {
        return Objects.isNull(excelCellReference) ? 1 : excelCellReference.getRow();
    }
}