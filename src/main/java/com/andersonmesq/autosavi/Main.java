package com.andersonmesq.autosavi;

import com.andersonmesq.autosavi.data.automacaoData;
import com.andersonmesq.autosavi.service.AutoMaster;
import com.andersonmesq.autosavi.automation.automacao;
import com.andersonmesq.autosavi.pages.saviCadastro;
import com.andersonmesq.autosavi.service.LeituraPlanilha;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.driver.driverSelerium;
import com.andersonmesq.autosavi.selenium.Selenium;

import java.awt.*;

//07483697326 ray102030
//60714652300 MA86322917

/*
Erro inesperado:

SEVERE: Erro ao tentar selecionar campo: element click intercepted: Element <input id="formulario:dataInicialInput" type="text" name="formulario:dataInicialInput" value="12/09/2025" class="form-control"> is not clickable at point (432, 422). Other element would receive the click: <div id="statusLoading" class="statusLoading" style="display: block;"></div>

Nov 17, 2025 7:31:26 PM com.andersonmesq.autosavi.selenium.Selenium selecionarCampo
SEVERE: Detalhes do erro:
org.openqa.selenium.ElementClickInterceptedException: element click intercepted: Element <input id="formulario:dataInicialInput" type="text" name="formulario:dataInicialInput" value="12/09/2025" class="form-control"> is not clickable at point (432, 422). Other element would receive the click: <div id="statusLoading" class="statusLoading" style="display: block;"></div>

Robot não consegue escrever "/"

Migrar para 100% selenium
*/
public class Main {
    public static void main(String[] args) throws AWTException {
        Planilha planilha = new Planilha();
        saviCadastro savi = new saviCadastro();
        LeituraPlanilha leituraPlanilha = new LeituraPlanilha();
        automacaoData automacaoData = new automacaoData();
        Robot robot = new Robot();
        automacao automacao = new automacao(robot);
        driverSelerium driverS = new driverSelerium();
        Selenium selenium = new Selenium();

        AutoMaster autoMaster = new AutoMaster(planilha, savi, leituraPlanilha, automacaoData, automacao, driverS, selenium);

        autoMaster.autoStart();
    }
}