package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.controller.AutomationController;
import com.andersonmesq.autosavi.data.AutomacaoData;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.pages.SaviPage;
import org.openqa.selenium.WebDriver;

public class AppFactory {
    private static final AppFactory instante = new AppFactory();
    private final AutomationController automationController;

    public AppFactory() {
        SaviPage saviPage = new SaviPage();
        SeleniumActions seleniumActions = new SeleniumActions();
        DriverFactory driverFactory = new DriverFactory();

        this.automationController = new AutomationController(driverFactory, saviPage, seleniumActions);
    }

    public static AppFactory getInstance() {
        return instante;
    }

    public AutomationController getAutomationController() {
        return automationController;
    }
}