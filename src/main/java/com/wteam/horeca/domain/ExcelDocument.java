package com.wteam.horeca.domain;

import com.google.common.io.Files;
import com.wteam.horeca.exceptions.ExcelDocumentIOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
public class ExcelDocument {

    private String fileName;
    private String fileDisplayName;
    private int sheetIndex;
    private int startRow;

    public ExcelDocument(String fileName) throws ExcelDocumentIOException {
        this(fileName, null);
    }

    public ExcelDocument(String fileName, String fileDisplayName) throws ExcelDocumentIOException {
        this(fileName, fileDisplayName, 0);
    }

    public ExcelDocument(String fileName, String fileDisplayName, int sheetIndex) throws ExcelDocumentIOException {
        this(fileName, fileDisplayName, 0, 0);
    }

    public ExcelDocument(String fileName, String fileDisplayName, int sheetIndex, int startRow) throws ExcelDocumentIOException {

        this.fileName = fileName;
        this.sheetIndex = sheetIndex;
        this.startRow = startRow;

        if (Objects.isNull(fileDisplayName)) {
            fileDisplayName = Files.getNameWithoutExtension(fileName);
        }

        this.fileDisplayName = fileDisplayName;

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);

            Map<Integer, List<String>> data = new HashMap<>();
            int i = 0;
            for (Row row : sheet) {
                data.put(i, new ArrayList<>());
                for (Cell cell : row) {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            data.get(i).add(cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                data.get(i).add(cell.getDateCellValue() + "");
                            } else {
                                data.get(i).add(cell.getNumericCellValue() + "");
                            }
                            break;
                        case BOOLEAN:
                            data.get(i).add(cell.getBooleanCellValue() + "");
                            break;
                        case FORMULA:
                            data.get(i).add(cell.getCellFormula() + "");
                            break;
                        default:
                            data.get(i).add(" ");
                    }
                }
                i++;
            }

        } catch (IOException e) {
            log.error("Error reading file {}", fileName, e);
            throw new ExcelDocumentIOException(e);
        } catch (InvalidFormatException e) {
            log.error("INVALID file format {}", fileName, e);
            throw new ExcelDocumentIOException(e);
        }
    }
}