package com.andersonmesq.autosavi;

import com.andersonmesq.autosavi.data.AutomacaoData;
import com.andersonmesq.autosavi.service.AutoMaster;
import com.andersonmesq.autosavi.automation.Automacao;
import com.andersonmesq.autosavi.pages.SaviCadastro;
import com.andersonmesq.autosavi.service.LeituraPlanilha;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.actions.SeleniumActions;

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
        SaviCadastro savi = new SaviCadastro();
        LeituraPlanilha leituraPlanilha = new LeituraPlanilha();
        AutomacaoData automacaoData = new AutomacaoData();
        Robot robot = new Robot();
        Automacao automacao = new Automacao(robot);
        DriverFactory driverS = new DriverFactory();
        SeleniumActions selenium = new SeleniumActions();

        AutoMaster autoMaster = new AutoMaster(planilha, savi, leituraPlanilha, automacaoData, automacao, driverS, selenium);

        autoMaster.autoStart();
    }
}