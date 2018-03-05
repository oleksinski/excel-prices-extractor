package com.wteam.horeca.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class ExcelLaunchService {

    public void execute(String filePath) throws IOException {
        execute(new File(filePath));
    }

    public void execute(File file) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        }
    }
}
