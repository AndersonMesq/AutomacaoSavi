package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.enums.AutomationState;
import com.andersonmesq.autosavi.enums.TipoSite;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.pages.SaviPage;
import com.andersonmesq.autosavi.service.AutomationService;
import com.andersonmesq.autosavi.factory.StrategyFactory;
import com.andersonmesq.autosavi.strategy.SiteStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.andersonmesq.autosavi.enums.AutomationState.*;

public class AutomationController {
    private final DriverFactory driverFactory;
    private final SaviPage saviPage;
    private final SeleniumActions seleniumActions;
    private final AutomationService automationService;
    private SiteStrategy strategy;
    private AutomationState state = AutomationState.IDLE;

    public AutomationController(DriverFactory driverFactory, SaviPage saviPage, SeleniumActions seleniumActions, AutomationService automationService) {
        this.driverFactory = driverFactory;
        this.saviPage = saviPage;
        this.seleniumActions = seleniumActions;
        this.automationService = automationService;
    }

    public void start(Prestador prestador, AutomationContext automationContext, Consumer<String> log) {
        if (state == AutomationState.PAUSED) {
            resume();
            log.accept("Automação retomada.");
            return;
        }

        if (state == AutomationState.RUNNING) {
            log.accept("Automação já está em execução.");
            return;
        }
        state = AutomationState.RUNNING;
        automationService.start(
                strategy,
                prestador,
                automationContext,

                // onFinish
                () -> {
                    state = AutomationState.FINISHED;
                    log.accept("Automação finalizada.");
                },

                // onError
                (e) -> {
                    state = AutomationState.CANCELLED;
                    log.accept("Erro: " + e.getMessage());
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
        System.out.println("Automação pausada");
    }

    public void prepare(TipoSite site) {
        strategy = StrategyFactory.create(site, driverFactory, seleniumActions);
        if (driverFactory.getDriver() == null) {
            driverFactory.openSavi();
        }
    }

    public boolean sheetValidation(File arquivo) {
        try (FileInputStream fis = new FileInputStream(arquivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row cabecalho = sheet.getRow(0);

            java.util.List<String> colunasObrigatorias = Arrays.asList("senha", "quantidade", "tipoAto", "data",
                    "hora", "viaAcesso", "valor", "OBS", "mensagem", "cadastro");
            Map<String, Integer> colunas = new HashMap<>();

            for (String coluna : colunasObrigatorias) {
                for (Cell cell : cabecalho) {
                    if (cell.getStringCellValue().trim().equalsIgnoreCase(coluna)) {
                        colunas.put(coluna, cell.getColumnIndex());
                    }
                }
            }

            if (colunas.size() < colunasObrigatorias.size()) {
                throw new RuntimeException("Colunas não encontrada ou com nome incorreto");
            } else {
                System.out.println("Colunas validadas com sucesso\n");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Não foi possivel validar: " + e);
            return false;
        }
    }

    public boolean isTelaCadastro() {
        try {
            WebDriver driver = driverFactory.getDriver();
            driver.findElement(saviPage.getCampoSenha());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Prestador> carregarPrestadores() {
        return strategy.loadPrestadores();
    }

    public void setState(AutomationState state) {
        this.state = state;
    }
}