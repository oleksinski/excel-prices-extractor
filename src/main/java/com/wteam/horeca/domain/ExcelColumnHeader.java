package com.wteam.horeca.domain;

public enum ExcelColumnHeader {
    DISTRIBUTOR("DISTRIBUTOR"),
    ID("ID"),
    BARCODE("Barcode"),
    NAME("Name"),
    DESCRIPTION("Description"),
    VENDOR("Vendor"),
    COUNTRY("Country"),
    REGION("Region"),
    CELLAR("Cellar"),
    YEAR("Year"),
    DATE("Date"),
    COLOR("Color"),
    SWEETNESS("Sweetness"),
    SOIL_TYPE("Soil type"),
    VINTAGE("Vintage"),
    RANKING("Ranking"),
    GRAPE_TYPE("Grape type"),
    CAPACITY("Capacity"),
    ALCOHOL("Alcohol"),
    KLASS("Class"),
    STOCK("Stock"),
    PRICE("Price");

    private String s;

    ExcelColumnHeader(String s) {
        this.s = s;
    }

    public static ExcelColumnHeader getIdByName(String name) {
        for (ExcelColumnHeader h : ExcelColumnHeader.values()) {
            if (h.toString().equals(name)) {
                return h;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return s;
    }
}
