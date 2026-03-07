import com.andersonmesq.autosavi.data.automacaoData;
import com.andersonmesq.autosavi.service.autoMaster;
import com.andersonmesq.autosavi.automation.automacao;
import com.andersonmesq.autosavi.pages.saviCadastro;
import com.andersonmesq.autosavi.service.leituraPlanilha;
import com.andersonmesq.autosavi.model.planilha;
import com.andersonmesq.autosavi.driver.driverSelerium;
import com.andersonmesq.autosavi.actions.seleniumActions;

import java.awt.*;

//60714652300 MA86322917

public class MainTest {
    public static void main(String[] args) throws AWTException {
        planilha planilha = new planilha();
        saviCadastro savi = new saviCadastro();
        leituraPlanilha leituraPlanilha = new leituraPlanilha();
        automacaoData automacaoData = new automacaoData();
        Robot robot = new Robot();
        automacao automacao = new automacao(robot);
        driverSelerium driverS = new driverSelerium();
        seleniumActions selenium = new seleniumActions();

        autoMaster autoMaster = new autoMaster(planilha, savi, leituraPlanilha, automacaoData, automacao, driverS, selenium);

        autoMaster.autoStart();
    }
}