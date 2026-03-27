package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.automation.SaviStrategy;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.strategy.SiteStrategy;
import com.andersonmesq.autosavi.enums.TipoSite;
import com.andersonmesq.autosavi.pages.SaviPage;
import org.openqa.selenium.WebDriver;

public class StrategyFactory {
    DriverFactory driverFactory = new DriverFactory();

    public static SiteStrategy create(TipoSite site, DriverFactory driverFactory, SeleniumActions selenium) {
        switch (site) {
            case SAVI:
                return new SaviStrategy(selenium, driverFactory);
//            case PORTAL:
//                return new PortalStrategy();
            default:
                throw new IllegalArgumentException("Site invalido");
        }
    }
}
