package com.andersonmesq.autosavi;

import com.andersonmesq.autosavi.controller.MainController;
import com.andersonmesq.autosavi.utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main-overlay.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        SceneManager.setMainController(mainController);

        FXMLLoader telaLoader = new FXMLLoader(getClass().getResource("/ui/select-site.fxml"));
        Node tela = telaLoader.load();

        mainController.setContent(tela, com.andersonmesq.autosavi.enums.Screen.FILE_CONFIG);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(bounds.getMaxX() - 340);
        primaryStage.setY(20);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}