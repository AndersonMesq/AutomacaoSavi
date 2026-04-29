package com.andersonmesq.autosavi.factory;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.automation.SaviStrategy;
import com.andersonmesq.autosavi.service.BrowserManager;
import com.andersonmesq.autosavi.strategy.SiteStrategy;
import com.andersonmesq.autosavi.enums.TipoSite;

public class StrategyFactory {
    DriverFactory driverFactory = new DriverFactory();

    public static SiteStrategy create(TipoSite site, BrowserManager browserManager, SeleniumActions selenium) {
        switch (site) {
            case SAVI:
                return new SaviStrategy(selenium, browserManager);
//            case PORTAL:
//                return new PortalStrategy();
            default:
                throw new IllegalArgumentException("Site invalido");
        }
    }
}
