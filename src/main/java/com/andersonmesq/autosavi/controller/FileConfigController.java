package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.enums.Screen;
import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.service.BrowserManager;
import com.andersonmesq.autosavi.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileConfigController {
    private static final Logger log = LoggerFactory.getLogger(FileConfigController.class);
    private File arquivoSelecionado;
    private boolean planilhaValida = false;
    private AutomationController controller;
    private BrowserManager browserManager;

    public void initialize() {
        controller = AppFactory.getInstance().getAutomationController();
        browserManager = AppFactory.getInstance().getBrowserManager();
    }

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
        if(!browserManager.checkBrowser(Screen.FILE_CONFIG)){
            return;
        }
        AutomationContext automationContext = AppFactory.getInstance().getAutomationContext();
        if (arquivoSelecionado == null) {
            SceneManager.atualizarStatus("Selecione um arquivo primeiro");
            return;
        }
        try {
            planilhaValida = controller.sheetValidation(arquivoSelecionado);
            if (planilhaValida) {
                automationContext.setArquivo(arquivoSelecionado);
            }
            SceneManager.atualizarStatus(planilhaValida
                    ? "Planilha validada com sucesso"
                    : "Planilha fora dos padrões");

        } catch (Exception e) {
            SceneManager.atualizarStatus("Erro ao validar planilha: " + e.getMessage());
            log.debug("Erro ao tentar validar planilha: ", e);
        }
    }

    @FXML
    private void onAvancar() {
        if(!browserManager.checkBrowser(Screen.FILE_CONFIG)){
            return;
        }
        if (!planilhaValida) {
            SceneManager.atualizarStatus("Valide a planilha antes de continuar");
            return;
        }
        if (!controller.isTelaCadastro()) {
            SceneManager.atualizarStatus("Você não está na tela de cadastro");
            return;
        }
        SceneManager.loadContent("autoConfig.fxml", Screen.AUTO_CONFIG);
    }

    @FXML
    private void onCancelar() {
        controller.cancel();
    }




}