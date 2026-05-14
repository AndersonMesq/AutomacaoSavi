package com.andersonmesq.autosavi;

import com.andersonmesq.autosavi.controller.MainController;
import com.andersonmesq.autosavi.utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

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

        primaryStage.getIcons().add(
                new Image(getClass().getResourceAsStream("/icon/logotipo-savi.png"))
        );
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
        try {
            FileLock lock;
            FileChannel channel;

            Path lockPath = Paths.get(
                    System.getenv("LOCALAPPDATA"),
                    "AutoSavi",
                    "autosavi.lock"
            );

            Files.createDirectories(lockPath.getParent());

            RandomAccessFile file = new RandomAccessFile(lockPath.toFile(), "rw");

            channel = file.getChannel();

            lock = channel.tryLock();

            if (lock == null) {
                log.debug("Tentatira de reabrir aplicativo detectada, app ja aberto");
                System.exit(0);
            }

        } catch (Exception e) {
            log.debug("Erro ao tentar iniciar aplicativo: ", e);
            System.exit(1);
        }

        launch(args);
    }
}