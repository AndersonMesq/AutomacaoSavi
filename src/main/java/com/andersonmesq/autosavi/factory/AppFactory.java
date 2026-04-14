package com.andersonmesq.autosavi.factory;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.controller.AutomationController;
import com.andersonmesq.autosavi.data.AutomacaoData;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.pages.SaviPage;
import com.andersonmesq.autosavi.service.AutoMaster;
import com.andersonmesq.autosavi.service.AutomationService;
import com.andersonmesq.autosavi.service.LeituraPlanilha;

public class AppFactory {

    private static final AppFactory instance = new AppFactory();

    private final AutomationController automationController;

    private final DriverFactory driverFactory;
    private final SeleniumActions seleniumActions;
    private final LeituraPlanilha leituraPlanilha;
    private final AutomationService automationService;
    private Planilha planilha;
    private AutomacaoData automacaoData;
    private AutomationContext automationContext;
    private AutoMaster autoMaster;
    private SaviPage saviPage;

    private AppFactory() {

        this.driverFactory = new DriverFactory();
        this.seleniumActions = new SeleniumActions();
        this.saviPage = new SaviPage();
        this.planilha = new Planilha();
        this.leituraPlanilha = new LeituraPlanilha();
        this.automacaoData = new AutomacaoData();
        this.automationContext = new AutomationContext();

        this.autoMaster = new AutoMaster(planilha, leituraPlanilha, automacaoData, seleniumActions);

        this.automationService = new AutomationService(autoMaster);
        this.automationController = new AutomationController(driverFactory, saviPage, seleniumActions, automationService);
    }

    public static AppFactory getInstance() {
        return instance;
    }

    public AutomationController getAutomationController() {
        return automationController;
    }

    public AutomationService getAutomationService() {
        return automationService;
    }

    public AutomationContext getAutomationContext() {
        return automationContext;
    }

    public void setAutomationContext(AutomationContext context) {
        this.automationContext = context;
    }

    public AutoMaster getAutoMaster(){
        return autoMaster;
    }
}