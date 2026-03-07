import com.andersonmesq.autosavi.data.automacaoData;
import com.andersonmesq.autosavi.service.AutoMaster;
import com.andersonmesq.autosavi.automation.automacao;
import com.andersonmesq.autosavi.pages.saviCadastro;
import com.andersonmesq.autosavi.service.LeituraPlanilha;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.driver.driverSelerium;
import com.andersonmesq.autosavi.selenium.Selenium;

import java.awt.*;

//60714652300 MA86322917

public class MainTest {
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