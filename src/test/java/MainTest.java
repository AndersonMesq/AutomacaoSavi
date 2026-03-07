import com.andersonmesq.autosavi.data.AutomacaoData;
import com.andersonmesq.autosavi.service.AutoMaster;
import com.andersonmesq.autosavi.automation.Automacao;
import com.andersonmesq.autosavi.pages.SaviCadastro;
import com.andersonmesq.autosavi.service.LeituraPlanilha;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.actions.SeleniumActions;

import java.awt.*;

//60714652300 MA86322917

public class MainTest {
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