package com.andersonmesq.autosavi.utils;

import com.andersonmesq.autosavi.controller.MainController;
import com.andersonmesq.autosavi.enums.Screen;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SceneManager {
    private static final Logger log = LoggerFactory.getLogger(SceneManager.class);
    private static MainController mainController;

    public static void loadContent(String fxml, Screen screen) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/ui/" + fxml));
            Node node = loader.load();

            mainController.setContent(node, screen);

            statusUpdate("...");
        } catch (IOException e) {
            log.debug("Erro ao executar loadContent(SceneManager): ", e);
        }
    }

    public static void statusUpdate(String mensagem) {
        if (mainController != null) {
            Platform.runLater(() -> mainController.statusUpdate(mensagem));
        }
    }

    public static void setMainController(MainController controller) {
        mainController = controller;
    }
}