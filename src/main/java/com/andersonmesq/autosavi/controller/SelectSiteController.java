package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.enums.Screen;
import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.enums.TipoSite;
import com.andersonmesq.autosavi.utils.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectSiteController {
    private static final Logger log = LoggerFactory.getLogger(SelectSiteController.class);

    @FXML
    private ComboBox<TipoSite> comboSite;

    private AutomationController controller;

    public void initialize() {
        controller = AppFactory.getInstance().getAutomationController();
        comboSite.getItems().addAll(TipoSite.values());
    }

    @FXML
    private void onNext() {
        TipoSite site = comboSite.getValue();
        if (site == null) {
            log.debug("Site valor null");
            return;
        }
        log.debug("{} selecionado", site);
        new Thread(() -> {
            try {
                controller.prepare(site);
                Platform.runLater(() -> SceneManager.loadContent("fileConfig.fxml", Screen.FILE_CONFIG));
            } catch (Exception e) {
                log.debug("Erro ao iniciar: ", e);
            }
        }).start();
    }
}