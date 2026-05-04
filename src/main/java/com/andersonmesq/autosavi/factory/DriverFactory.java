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
        String driverPath = extractDriver();

        System.setProperty("webdriver.edge.driver", driverPath);

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");

        return new EdgeDriver(options);
    }

    private String extractDriver() {
        try (InputStream input = getClass()
                .getResourceAsStream("/drivers/msedgedriver.exe")) {

            if (input == null) {
                throw new RuntimeException("Driver não encontrado no JAR");
            }

            File tempFile = File.createTempFile("msedgedriver", ".exe");
            tempFile.deleteOnExit();

            Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return tempFile.getAbsolutePath();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair driver", e);
        }
    }
}
