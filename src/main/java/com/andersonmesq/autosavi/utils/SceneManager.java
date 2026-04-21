package com.andersonmesq.autosavi.utils;

import com.andersonmesq.autosavi.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SceneManager {
    private static final Logger log = LoggerFactory.getLogger(SceneManager.class);

    public static void loadContent(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/ui/" + fxml));
            System.out.println("fxml: " + fxml);
            Node node = loader.load();

            mainController.setContent(node);

        } catch (IOException e) {
            log.debug("Erro ao executar loadContent(SceneManager): ", e);
        }
    }

    private static MainController mainController;

    public static void setMainController(MainController controller) {
        mainController = controller;
    }
}