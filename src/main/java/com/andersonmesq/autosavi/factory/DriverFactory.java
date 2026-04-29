package com.andersonmesq.autosavi.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {
    public WebDriver createEdge() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\ander\\IdeaProjects\\ProjetoAutoSavi\\src\\main\\resources\\DriverEdge\\msedgedriver.exe");
//        WebDriverManager.edgedriver().setup();

        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("detach", false);
        options.addArguments("--start-maximized");

        return new EdgeDriver(options);
    }
}
