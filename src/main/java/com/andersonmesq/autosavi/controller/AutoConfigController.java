package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.enums.Screen;
import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.service.BrowserManager;
import com.andersonmesq.autosavi.service.UiAppender;
import com.andersonmesq.autosavi.utils.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AutoConfigController {
    private static final Logger log = LoggerFactory.getLogger(AutoConfigController.class);
    private BrowserManager browserManager;
    private AutomationController controller;
    private AutomationContext automationContext;


    public void initialize() {
        controller = AppFactory.getInstance().getAutomationController();
        automationContext = AppFactory.getInstance().getAutomationContext();
        browserManager = AppFactory.getInstance().getBrowserManager();
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
        if(!browserManager.checkBrowser(Screen.AUTO_CONFIG)){
            return;
        }
        SceneManager.atualizarStatus("Carregando prestadores");

        new Thread(() -> {
            try {
                List<Prestador> lista = controller.carregarPrestadores();
                Platform.runLater(() -> {
                    comboPrestadores.getItems().setAll(lista);
                    SceneManager.atualizarStatus("Prestadores carregados");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    SceneManager.atualizarStatus("Erro ao carregar prestadores");
                    log.debug("Erro ao tentar carregar prestadores: ", e);
                });
            }
        }).start();
    }

    @FXML
    private void onIniciar() {
        if(!browserManager.checkBrowser(Screen.AUTO_CONFIG)){
            return;
        }
        Prestador prestador = comboPrestadores.getValue();
        if (prestador == null) {
            SceneManager.atualizarStatus("Selecione um prestador");
            return;
        }
        controller.start(prestador, automationContext);
    }

    @FXML
    private void onPausar() {
        if(!browserManager.checkBrowser(Screen.AUTO_CONFIG)){
            return;
        }
        SceneManager.atualizarStatus("Automação pausada");
        controller.pause();
    }

    @FXML
    private void onCancelar() {
        if(!browserManager.checkBrowser(Screen.AUTO_CONFIG)){
            return;
        }
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
}