package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.enums.Screen;
import com.andersonmesq.autosavi.enums.TooltipType;
import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.utils.SceneManager;
import com.andersonmesq.autosavi.utils.TooltipFactory;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @FXML
    private Button btnPlanilha;

    @FXML
    private Button btnHome;

    @FXML
    public void initialize() {
        TooltipFactory.install(btnHome, TooltipType.HOME.getText());
        TooltipFactory.install(btnPlanilha, TooltipType.GERAR_PLANILHA.getText());
    }

    @FXML
    private VBox container;

    private Screen currentScreen;

    @FXML
    private void onBack() {
        if (currentScreen == Screen.AUTO_CONFIG) {
            SceneManager.loadContent("fileConfig.fxml", Screen.FILE_CONFIG);
        }
    }

    @FXML
    private void onHome() {
        boolean browserDead = AppFactory.getInstance()
                .getBrowserManager()
                .isBrowserAlive();

        if (!browserDead) {
            SceneManager.loadContent("select-site.fxml", Screen.HOME);
        }
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) container.getScene().getWindow();
        AppFactory.getInstance().getBrowserManager().close();
        stage.close();
    }

    @FXML
    private Label lblStatus;

    @FXML
    private void onGerarPlanilhaModelo() {
        String userHome = System.getProperty("user.home");
        File downloadsDir = new File(userHome, "Downloads");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar planilha modelo");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx")
        );

        fileChooser.setInitialFileName("Planilha-modelo.xlsx");

        if (downloadsDir.exists()) {
            fileChooser.setInitialDirectory(downloadsDir);
        }

        Stage stage = (Stage) container.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            copiarTemplatePara(file);

            try {
                Desktop.getDesktop().open(file.getParentFile());
            } catch (IOException e) {
                log.debug("Erro ao abrir arquivos: ", e);
            }
        }
    }

    public void atualizarStatus(String mensagem) {
        lblStatus.setText(mensagem);
    }

    public void setContent(Node node, Screen screen) {
        container.getChildren().setAll(node);
        this.currentScreen = screen;
    }

    private void copiarTemplatePara(File destino) {
        try (InputStream is = getClass().getResourceAsStream("/Planilha-modelo.xlsx");
             FileOutputStream fos = new FileOutputStream(destino)) {

            if (is == null) {
                throw new RuntimeException("Template não encontrado nos resources");
            }

            is.transferTo(fos);

            SceneManager.atualizarStatus("Planilha modelo salva com sucesso!");

        } catch (IOException e) {
            SceneManager.atualizarStatus("Erro ao salvar planilha modelo");
            log.debug("Erro ao tentar criar copia da planilha modelo: ", e);
        }
    }
}