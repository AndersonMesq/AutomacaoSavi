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
    private void onSelectFile() {
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
    private void onValidateSheet() {
        if(!browserManager.checkBrowser()){
            return;
        }
        AutomationContext automationContext = AppFactory.getInstance().getAutomationContext();
        if (arquivoSelecionado == null) {
            SceneManager.statusUpdate("Selecione um arquivo primeiro");
            return;
        }
        try {
            planilhaValida = controller.sheetValidation(arquivoSelecionado);
            if (planilhaValida) {
                automationContext.setArquivo(arquivoSelecionado);
            }
            SceneManager.statusUpdate(planilhaValida
                    ? "Planilha validada com sucesso"
                    : "Planilha fora dos padrões");

        } catch (Exception e) {
            SceneManager.statusUpdate("Erro ao validar planilha: " + e.getMessage());
            log.debug("Erro ao tentar validar planilha: ", e);
        }
    }

    @FXML
    private void onAdvance() {
        if(!browserManager.checkBrowser()){
            return;
        }
        if (!planilhaValida) {
            SceneManager.statusUpdate("Valide a planilha antes de continuar");
            return;
        }
        if (!controller.isTelaCadastro()) {
            SceneManager.statusUpdate("Você não está na tela de cadastro");
            return;
        }
        SceneManager.loadContent("autoConfig.fxml", Screen.AUTO_CONFIG);
    }

    @FXML
    private void onCancel() {
        controller.cancel();
    }




}