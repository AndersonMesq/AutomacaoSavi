package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.strategy.SiteStrategy;
import com.andersonmesq.autosavi.utils.LogMarkers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class AutomationService {
    private static final Logger log = LoggerFactory.getLogger(AutomationService.class);
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
                LogMarkers.user(log, "Automação não iniciada, erro: {}", e.getMessage());
                log.debug("Erro ao iniciar a automação: ", e);
            }
        }).start();
    }
}