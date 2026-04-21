package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserManager {
    private static final Logger log = LoggerFactory.getLogger(BrowserManager.class);
    private WebDriver driver;
    private final DriverFactory factory = new DriverFactory();

    public void openSavi() {
        try {
            driver = factory.createEdge();
            driver.get("https://saviatendimento.com.br/saviatendimento/login.faces");
        } catch (Exception e) {
            log.debug("Erro ao tentar abrir navegador: ", e);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void ensureBrowser() {
        if (!isBrowserAlive()) {
            log.debug("Reiniciando browser...");
            close();
            openSavi();
        }
    }

    public boolean isBrowserAlive() {
        if (driver == null) return false;

        try {
            driver.getWindowHandle();
            return true;
        } catch (Exception e) {
            log.debug("Browser morto detectado");
            return false;
        }
    }

    public void close() {
        if (driver != null) {
            try {
                log.debug("Fechando driver: {}", driver);
                driver.quit();
            } catch (Exception ignored) {
            }
        }
    }
}