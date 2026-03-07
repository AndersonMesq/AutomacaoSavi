package com.andersonmesq.autosavi.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {
    private WebDriver driver;

    public void openSavi() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\ander\\IdeaProjects\\ProjetoAutoSavi\\DriverEdge\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("detach", true);
        options.addArguments("--start-maximized");

        driver = new EdgeDriver(options);
        driver.get("https://saviatendimento.com.br/saviatendimento/login.faces");
    }

    public WebDriver getDriver() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\ander\\IdeaProjects\\ProjetoAutoSavi\\DriverEdge\\msedgedriver.exe");
        return driver;
    }
}
