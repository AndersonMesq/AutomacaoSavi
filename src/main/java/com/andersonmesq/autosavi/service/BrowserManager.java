package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.factory.DriverFactory;
import com.andersonmesq.autosavi.enums.Screen;
import com.andersonmesq.autosavi.utils.SceneManager;
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
            driver.getTitle();
            return true;
        } catch (Exception e) {
            log.debug("Browser morto detectado");
            try {
                driver.quit();
            } catch (Exception ignored) {}
            driver = null;

            return false;
        }
    }

    public boolean checkBrowser(Screen screen) {
        if (!isBrowserAlive()) {
//            LogMarkers.user(log, "Navegador fechado. Voltando para seleção");
//            Platform.runLater(() ->
//                    SceneManager.loadContent("select-site.fxml", screen)
//            );
            SceneManager.atualizarStatus("Navegador fechado, clique em \uD83C\uDFE0 para reabrir");
            return false;
        }
        return true;
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