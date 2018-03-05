package com.wteam.horeca.domain;

import lombok.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ExcelSheet {
    private List<ExcelSheetRow> excelSheetRows;

    public List<ExcelSheetRow> getExcelSheetRows() {
        return excelSheetRows != null ? excelSheetRows : new CopyOnWriteArrayList<>(); // To avoid null pointer exceptions.
    }

    public static ExcelSheet of(ExcelSheet entity) {
        if (entity == null) {
            return null;
        }
        return entity.toBuilder()
                .excelSheetRows(ExcelSheetRow.copyList(entity.getExcelSheetRows()))
                .build();
    }

    public static List<String> copyList(List<String> entities) {
        if (entities == null) {
            return new CopyOnWriteArrayList<>();
        }
        return new CopyOnWriteArrayList<>(entities);
    }
}
