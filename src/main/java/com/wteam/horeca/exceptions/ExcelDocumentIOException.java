package com.wteam.horeca.exceptions;

import java.io.IOException;

public class ExcelDocumentIOException extends IOException {

    private final static String EXCEPTION_MESSAGE = "Error occurred while reading excel document";

    public ExcelDocumentIOException(Throwable cause) {
        super(EXCEPTION_MESSAGE, cause);
    }

    public ExcelDocumentIOException(String message) {
        super(message);
    }
}
