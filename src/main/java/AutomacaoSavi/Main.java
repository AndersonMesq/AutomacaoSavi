package AutomacaoSavi;

import AutomacaoSavi.AutoData.AutomacaoData;
import AutomacaoSavi.Automacao.AutoMaster;
import AutomacaoSavi.Automacao.Automacao;
import AutomacaoSavi.Automacao.Savi;
import AutomacaoSavi.Planilha.LeituraPlanilha;
import AutomacaoSavi.Planilha.Planilha;
import AutomacaoSavi.SeleniumDriver.DriverSelerium;
import AutomacaoSavi.SeleniumDriver.Selenium;

import java.awt.*;

//07483697326 ray102030
//60714652300 MA86322917

/*
Erro inesperado:

SEVERE: Erro ao tentar selecionar campo: element click intercepted: Element <input id="formulario:dataInicialInput" type="text" name="formulario:dataInicialInput" value="12/09/2025" class="form-control"> is not clickable at point (432, 422). Other element would receive the click: <div id="statusLoading" class="statusLoading" style="display: block;"></div>

Nov 17, 2025 7:31:26 PM AutomacaoSavi.SeleniumDriver.Selenium selecionarCampo
SEVERE: Detalhes do erro:
org.openqa.selenium.ElementClickInterceptedException: element click intercepted: Element <input id="formulario:dataInicialInput" type="text" name="formulario:dataInicialInput" value="12/09/2025" class="form-control"> is not clickable at point (432, 422). Other element would receive the click: <div id="statusLoading" class="statusLoading" style="display: block;"></div>

Robot não consegue escrever "/"

Migrar para 100% selenium
*/
public class Main {
    public static void main(String[] args) throws AWTException {
        Planilha planilha = new Planilha();
        Savi savi = new Savi();
        LeituraPlanilha leituraPlanilha = new LeituraPlanilha();
        AutomacaoData automacaoData = new AutomacaoData();
        Robot robot = new Robot();
        Automacao automacao = new Automacao(robot);
        DriverSelerium driverS = new DriverSelerium();
        Selenium selenium = new Selenium();

        AutoMaster autoMaster = new AutoMaster(planilha, savi, leituraPlanilha, automacaoData, automacao, driverS, selenium);

        autoMaster.autoStart();
    }
}