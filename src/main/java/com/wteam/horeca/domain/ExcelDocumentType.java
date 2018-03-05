package com.wteam.horeca.domain;

public enum ExcelDocumentType implements Comparable<ExcelDocumentType> {
    XLS("xls"),
    XLSX("xlsx");

    private String type;

    ExcelDocumentType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
