package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.model.Prestador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;

public class AutoConfigController {
    private AutomationController controller;
    private AutomationContext automationContext;

    public void initialize() {
        controller = AppFactory.getInstance().getAutomationController();
        automationContext = AppFactory.getInstance().getAutomationContext();
    }

    @FXML
    private ComboBox<Prestador> comboPrestadores;

    @FXML
    private void onAtualizarPrestadores() {
        initialize();
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
                    System.out.println(e.getMessage());
                });
            }
        }).start();
    }

    @FXML
    private void onIniciar() {
        Prestador prestador = comboPrestadores.getValue();
        if (prestador == null) {
            atualizarStatus("Selecione um prestador");
            return;
        }
        System.out.println("Prestador selecionado: " + prestador);
        controller.start(prestador, automationContext, System.out::println);
    }

    @FXML
    private void onPausar() {
        controller.pause();
    }

    @FXML
    private void onCancelar() {
        controller.cancel();
    }

    @FXML
    private Label txtAreaLogs;

    @FXML
    private Label lblStatus;

    private void atualizarStatus(String mensagem) {
        lblStatus.setText(mensagem);
    }

}

