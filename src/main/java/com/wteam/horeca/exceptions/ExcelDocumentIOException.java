package com.wteam.horeca.exceptions;

import java.io.IOException;

public class ExcelDocumentIOException extends IOException {

    public ExcelDocumentIOException(Throwable cause) {
        super("Error occurred while reading excel document", cause);
    }

    public ExcelDocumentIOException(String message) {
        super(message);
    }
}
