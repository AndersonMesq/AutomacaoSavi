package com.andersonmesq.autosavi.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DriverFactory {
    public WebDriver createEdge() {
        System.setProperty("webdriver.edge.driver", extractDriver());
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");

        return new EdgeDriver(options);
    }

    public static String extractDriver() {
        try {
            InputStream is = DriverFactory.class.getResourceAsStream("/drivers/msedgedriver.exe");

            if (is == null) {
                throw new RuntimeException("Driver NÃO encontrado no classpath!");
            }

            File tempFile = File.createTempFile("msedgedriver", ".exe");
            tempFile.deleteOnExit();

            Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return tempFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair driver", e);
        }
    }
}