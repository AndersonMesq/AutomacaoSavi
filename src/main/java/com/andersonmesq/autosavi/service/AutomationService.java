package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.controller.AutomationController;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.strategy.SiteStrategy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.function.Consumer;

public class AutomationService {
    private final AutomationController controller;
    private final AutoMaster autoMaster;
    private final DriverFactory driverS;

    public AutomationService(AutomationController controller, AutoMaster autoMaster, DriverFactory driverS) {
        this.controller = controller;
        this.autoMaster = autoMaster;
        this.driverS = driverS;
    }

    public void start(
            SiteStrategy strategy,
            Prestador prestador,
            File arquivo,
            Consumer<String> log,
            Runnable onFinish,
            Consumer<Exception> onError
    ) {

        new Thread(() -> {
            try {


                autoMaster.autoStart(strategy, prestador, arquivo, controller);

                onFinish.run();

            } catch (Exception e) {
                onError.accept(e);
            }
        }).start();
    }
}