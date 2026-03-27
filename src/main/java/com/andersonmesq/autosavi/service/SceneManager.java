package com.andersonmesq.autosavi.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage stage;

    public static void init(Stage stage) {
        SceneManager.stage = stage;
    }

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void load(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/" + fxml)
            );

            Parent root = loader.load();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void load(String fxml) {
//        try {
//            FXMLLoader loader = new FXMLLoader(
//                    SceneManager.class.getResource("/" + fxml)
//            );
//
//            loader.setControllerFactory(clazz -> {
//                if (clazz == SelectSiteController.class) {
//                    return new SelectSiteController(factory.getAutomationController());
//                }
//
//                if (clazz == ConfigController.class) {
//                    return new ConfigController(factory.getAutomationController());
//                }
//
//                try {
//                    return clazz.getDeclaredConstructor().newInstance();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            });
//
//            Parent root = loader.load();
//            stage.setScene(new Scene(root));
//            stage.show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
