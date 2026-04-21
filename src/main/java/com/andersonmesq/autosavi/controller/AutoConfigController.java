package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.service.UiAppender;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AutoConfigController {
    private static final Logger log = LoggerFactory.getLogger(AutoConfigController.class);
    private AutomationController controller;
    private AutomationContext automationContext;


    public void initialize() {
        controller = AppFactory.getInstance().getAutomationController();
        automationContext = AppFactory.getInstance().getAutomationContext();
        UiAppender.setUiConsumer(msg -> {
            logArea.appendText((msg + "\n"));
            logArea.setScrollTop(Double.MAX_VALUE);
        });
        debugCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> UiAppender.setDebugMode(newVal));
    }

    @FXML
    private ComboBox<Prestador> comboPrestadores;

    @FXML
    private void onAtualizarPrestadores() {
        controller.checkBrowser();
        atualizarStatus("Carregando prestadores");

        new Thread(() -> {
            try {
                List<Prestador> lista = controller.carregarPrestadores();
                Platform.runLater(() -> {
                    comboPrestadores.getItems().setAll(lista);
                    atualizarStatus("Prestadores carregados");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    atualizarStatus("Erro ao carregar prestadores");
                    log.debug("Erro ao tentar carregar prestadores: ", e);
                });
            }
        }).start();
    }

    @FXML
    private void onIniciar() {
        controller.checkBrowser();
        Prestador prestador = comboPrestadores.getValue();
        if (prestador == null) {
            atualizarStatus("Selecione um prestador");
            return;
        }
        controller.start(prestador, automationContext);
    }

    @FXML
    private void onPausar() {
        controller.checkBrowser();
        controller.pause();
    }

    @FXML
    private void onCancelar() {
        controller.checkBrowser();
        controller.cancel();
    }

    @FXML
    private TextArea logArea;

    @FXML
    private CheckBox debugCheckBox;

    @FXML
    private void copiarLog() {
        String logCompleto = UiAppender.getFullLog();
        ClipboardContent content = new ClipboardContent();
        content.putString(logCompleto);
        Clipboard.getSystemClipboard().setContent(content);
    }

    @FXML
    private Label lblStatus;

    private void atualizarStatus(String mensagem) {
        lblStatus.setText(mensagem);
    }

}