package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.factory.AppFactory;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private VBox container;

    @FXML
    private void onClose() {
        Stage stage = (Stage) container.getScene().getWindow();
        AppFactory.getInstance().getBrowserManager().close();
        stage.close();
    }

    public void setContent(Node node) {
        container.getChildren().setAll(node);
    }
}