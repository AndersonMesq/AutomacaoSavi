package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.strategy.SiteStrategy;

import java.util.function.Consumer;

public class AutomationService {
    private final AutoMaster autoMaster;

    public AutomationService(AutoMaster autoMaster) {
        this.autoMaster = autoMaster;
    }

    public void start(
            SiteStrategy strategy,
            Prestador prestador,
            AutomationContext automationContext,
            Runnable onFinish,
            Consumer<Exception> onError
    ) {
        new Thread(() -> {
            try {
                autoMaster.autoStart(strategy, prestador, automationContext);
                onFinish.run();
            } catch (Exception e) {
                onError.accept(e);
            }
        }).start();
    }
}