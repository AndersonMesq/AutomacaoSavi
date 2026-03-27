package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.service.AppFactory;
import com.andersonmesq.autosavi.model.Prestador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class ConfigController {

    @FXML
    private ComboBox<Prestador> comboPrestador;

    @FXML
    private Label labelStatus;

    @FXML
    private TextArea areaLogs;

    @FXML
    private TextField txtArquivo;

    private File arquivoSelecionado;

    private AutomationController controller;
    private Prestador prestador;

    public void initialize() {
        controller = AppFactory.getInstance().getAutomationController();
        carregarPrestadores();
    }

    @FXML
    private void onSelecionarArquivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel", "*.xlsx")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            arquivoSelecionado = file;
            txtArquivo.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void onStart() {
        controller.start(prestador, arquivoSelecionado, this::log);
    }

    @FXML
    private void onPause() {
        controller.pause();
        atualizarStatus("PAUSED");
    }

    @FXML
    private void onCancel() {
        controller.cancel();
        atualizarStatus("CANCELLED");
    }

    private void atualizarStatus(String status) {
        Platform.runLater(() ->
                labelStatus.setText("Status: " + status)
        );
    }

    private void log(String mensagem) {
        Platform.runLater(() ->
                areaLogs.appendText(mensagem + "\n")
        );
    }

    private void carregarPrestadores() {
        new Thread(() -> {
            try {
                List<Prestador> lista = controller.carregarPrestadores();
                Platform.runLater(() ->
                        comboPrestador.getItems().setAll(lista)
                );
            } catch (Exception e) {
                log("Erro ao carregar prestadores: " + e.getMessage());
            }
        }).start();
    }
}