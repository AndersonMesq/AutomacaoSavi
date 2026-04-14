package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;

public class FileConfigController {
    private File arquivoSelecionado;
    private boolean planilhaValida = false;
    private AutomationController controller;

    @FXML
    private Label lblArquivo;

    @FXML
    private void onSelecionarArquivo() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel", "*.xlsx")
        );

        File file = chooser.showOpenDialog(null);

        if (file != null) {
            this.arquivoSelecionado = file;
            lblArquivo.setText(file.getName());
        }
    }

    @FXML
    private void onValidarPlanilha() {
        controller = AppFactory.getInstance().getAutomationController();
        AutomationContext automationContext = AppFactory.getInstance().getAutomationContext();
        if (arquivoSelecionado == null) {
            atualizarStatus("Selecione um arquivo primeiro");
            return;
        }
        try {
            planilhaValida = controller.sheetValidation(arquivoSelecionado);
            if (planilhaValida) {
                automationContext.setArquivo(arquivoSelecionado);
            }
            atualizarStatus(planilhaValida
                    ? "Planilha validada com sucesso"
                    : "Planilha fora dos padrões");

        } catch (Exception e) {
            atualizarStatus("Erro ao validar planilha: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onAvancar() {

        if (!planilhaValida) {
            atualizarStatus("Valide a planilha antes de continuar");
            return;
        }

        boolean isCadastro = controller.isTelaCadastro();

        if (!isCadastro) {
            atualizarStatus("Você não está na tela de cadastro");
            return;
        }

        SceneManager.load("ui/autoConfig.fxml");
    }

    @FXML
    private void onCancelar() {
        controller.cancel();
        SceneManager.load("autoConfig.fxml");
    }

    @FXML
    private Label lblStatus;

    private void atualizarStatus(String mensagem) {
        lblStatus.setText(mensagem);
    }
}