package com.andersonmesq.autosavi.pages;

import org.openqa.selenium.By;

public class saviCadastro {
    private final By campoSenha = By.id("formulario:cd_senha");
    private final By imputSenha = By.id("formulario:btnPesquisaSenha");
    private final By campoQuantidade = By.id("formulario:quant_procedimento");
    private final By campoPrestador = By.id("formulario:prestador");
    private final By campoTipoAto = By.id("formulario:tipo_ato");
    private final By campoData = By.id("formulario:dataInicialInput");
    private final By campoHora = By.id("formulario:hora_atendimento");
    private final By campoViaAcesso = By.id("formulario:via_acesso");
    private final By campoValor = By.id("formulario:valor");
    private final By campoOBS = By.name("formulario:j_idt122"); //Não é um id, é um nome
    private final By imputGravar = By.id("formulario:j_idt124");
    private final By PopUptext = By.cssSelector("span.ui-messages-warn-summary, span.ui-messages-info-summary");
    private final By PopUpButton = By.cssSelector("button.btn.btn-info");

    public By getCampoSenha() {
        return campoSenha;
    }

    public By getImputSenha() {
        return imputSenha;
    }

    public By getCampoQuantidade() {
        return campoQuantidade;
    }

    public By getCampoPrestador() {
        return campoPrestador;
    }

    public By getCampoTipoAto() {
        return campoTipoAto;
    }

    public By getCampoData() {
        return campoData;
    }

    public By getCampoHora() {
        return campoHora;
    }

    public By getCampoViaAcesso() {
        return campoViaAcesso;
    }

    public By getCampoValor() {
        return campoValor;
    }

    public By getCampoOBS() {
        return campoOBS;
    }

    public By getImputGravar() {
        return imputGravar;
    }

    public By getPopUptext() {
        return PopUptext;
    }

    public By getPopUpButton() {
        return PopUpButton;
    }
}
