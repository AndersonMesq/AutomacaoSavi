package com.andersonmesq.autosavi;

import com.andersonmesq.autosavi.service.AppFactory;
import com.andersonmesq.autosavi.service.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

//07483697326 ray102030
//60714652300 MA86322917

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SceneManager.init(primaryStage);
        SceneManager.setStage(primaryStage);
        SceneManager.load("ui/select-site.fxml");
        primaryStage.setTitle("AutoSavi");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}