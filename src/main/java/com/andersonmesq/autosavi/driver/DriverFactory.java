package com.andersonmesq.autosavi.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {
    public WebDriver createEdge() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\ander\\IdeaProjects\\ProjetoAutoSavi\\DriverEdge\\msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("detach", false);
        options.addArguments("--start-maximized");

        return new EdgeDriver(options);
    }
}
