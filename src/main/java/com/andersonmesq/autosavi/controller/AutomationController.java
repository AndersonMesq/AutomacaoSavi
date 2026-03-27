package com.andersonmesq.autosavi.controller;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.enums.AutomationState;
import com.andersonmesq.autosavi.enums.TipoSite;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.pages.SaviPage;
import com.andersonmesq.autosavi.service.AutomationService;
import com.andersonmesq.autosavi.service.StrategyFactory;
import com.andersonmesq.autosavi.strategy.SiteStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

import static com.andersonmesq.autosavi.enums.AutomationState.*;

public class AutomationController {
    private final DriverFactory driverFactory;
    private final SaviPage saviPage;
    private final SeleniumActions seleniumActions;
    private SiteStrategy strategy;
    private AutomationState state = AutomationState.IDLE;
    private AutomationService automationService;

    public AutomationController(DriverFactory driverFactory, SaviPage saviPage, SeleniumActions seleniumActions) {
        this.driverFactory = driverFactory;
        this.saviPage = saviPage;
        this.seleniumActions = seleniumActions;
    }

    public void start(Prestador prestador, File arquivo, Consumer<String> log) {

        if (state == AutomationState.PAUSED) {
            resume();
            log.accept("Automação retomada.");
            return;
        }

        if (state == AutomationState.RUNNING) {
            log.accept("Automação já está em execução.");
            return;
        }
        // Quando adicionar o Portal provavelmente alterar o metodo isTelaCadastro
        WebDriver driver = driverFactory.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(d -> seleniumActions.isTelaCadastro(d, saviPage));

        state = AutomationState.RUNNING;
        automationService.start(
                strategy,
                prestador,
                arquivo,
                log,

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

    public List<Prestador> carregarPrestadores() {
        return strategy.loadPrestadores();
    }

    public void setState(AutomationState state) {
        this.state = state;
    }
}