package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.enums.AutomationState;
import com.andersonmesq.autosavi.enums.TipoSite;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.pages.SaviPage;
import com.andersonmesq.autosavi.service.AutomationService;
import com.andersonmesq.autosavi.factory.StrategyFactory;
import com.andersonmesq.autosavi.service.BrowserManager;
import com.andersonmesq.autosavi.strategy.SiteStrategy;
import com.andersonmesq.autosavi.utils.LogMarkers;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.andersonmesq.autosavi.enums.AutomationState.*;

public class AutomationController {
    private static final Logger log = LoggerFactory.getLogger(AutomationController.class);
    private final BrowserManager browserManager;
    private final SaviPage saviPage;
    private final SeleniumActions seleniumActions;
    private final AutomationService automationService;
    private SiteStrategy strategy;
    private AutomationState state = AutomationState.IDLE;

    public AutomationController(BrowserManager browserManager, SaviPage saviPage, SeleniumActions seleniumActions, AutomationService automationService) {
        this.browserManager = browserManager;
        this.saviPage = saviPage;
        this.seleniumActions = seleniumActions;
        this.automationService = automationService;
    }

    public void prepare(TipoSite site) {
        strategy = StrategyFactory.create(site, browserManager, seleniumActions);
        browserManager.ensureBrowser();
        log.debug("Browser pronto");
    }

    public void start(Prestador prestador, AutomationContext automationContext) {
        if (state == AutomationState.PAUSED) {
            resume();
            LogMarkers.user(log, "Automação retomada");
            return;
        }

        if (state == AutomationState.RUNNING) {
            LogMarkers.user(log, "Automação ja em andamento");
            return;
        }
        state = AutomationState.RUNNING;
        automationService.start(
                strategy,
                prestador,
                automationContext,

                () -> {
                    state = AutomationState.FINISHED;
                    LogMarkers.user(log, "Automação finalizada");
                },

                (e) -> {
                    state = AutomationState.CANCELLED;
                    LogMarkers.user(log, "Erro ao iniciar automação: ", e);
                }
        );
    }

    public void pause() {
        state = AutomationState.PAUSED;
    }

    public void resume() {
        state = AutomationState.RUNNING;
    }

    public void cancel() {
        state = AutomationState.CANCELLED;
    }

    public void checkAutoState() {
        if (state == CANCELLED) {
            throw new RuntimeException("Execução cancelada");
        }

        if (state == FINISHED) {
            throw new RuntimeException("Execução finalizada");
        }

        while (state == PAUSED) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean sheetValidation(File arquivo) {
        try (FileInputStream fis = new FileInputStream(arquivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row cabecalho = sheet.getRow(0);

            List<String> colunasObrigatorias = Arrays.asList("senha", "quantidade", "tipoAto", "data",
                    "hora", "viaAcesso", "valor", "OBS", "mensagem", "cadastro");
            Map<String, Integer> colunas = new HashMap<>();

            for (String coluna : colunasObrigatorias) {
                for (Cell cell : cabecalho) {
                    if (cell.getStringCellValue().trim().equalsIgnoreCase(coluna)) {
                        colunas.put(coluna, cell.getColumnIndex());
                    }
                }
            }
            return colunas.size() >= colunasObrigatorias.size();

        } catch (IOException e) {
            log.debug("Não foi possivel validar planilha, erro: ", e);
            return false;
        }
    }

    public boolean isTelaCadastro() {
        try {
            WebDriver driver = browserManager.getDriver();
            driver.findElement(saviPage.getCampoSenha());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void salvarLog(String caminho, String conteudo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho, true))) {
            writer.write(conteudo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Prestador> carregarPrestadores() {
        return strategy.loadPrestadores();
    }

    public void setState(AutomationState state) {
        this.state = state;
    }
}