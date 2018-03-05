package com.wteam.horeca.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ExcelSheetRow {
    private String id; //
    private String barcode; //
    private String name; // = appellation
    private String description; //
    private String vendor; //
    private String country; //
    private String region; //
    private String cellar; // podval
    private String year; //
    private String date; //
    private String color; //
    private String sweetness; // extra-dry, dry, medium, medium-sweet, sweet
    private String soilType; //
    private String vintage; //
    private String ranking; //
    private String grapeType; // shardone, pino-nuar, ...
    private String capacity; // bottle capacity
    private String alcohol; //
    private String klass; //
    private String stock; // = availability
    private String price; //

    public static ExcelSheetRow of(ExcelSheetRow entity) {
        if (entity == null) {
            return null;
        }
        return entity.toBuilder().build();
    }

    static List<ExcelSheetRow> copyList(List<ExcelSheetRow> list) {
        List<ExcelSheetRow> result = new ArrayList<>();
        if (list != null) {
            result.addAll(list.stream().map(ExcelSheetRow::of).collect(Collectors.toList()));
        }
        return result;
    }
}
