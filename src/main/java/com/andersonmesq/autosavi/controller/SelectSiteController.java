package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.enums.TipoSite;
import com.andersonmesq.autosavi.utils.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class SelectSiteController {

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

        if (site == null) return;

        new Thread(() -> {
            try {
                controller.prepare(site);

                Platform.runLater(() -> {
                    SceneManager.load("ui/fileConfig.fxml");
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
