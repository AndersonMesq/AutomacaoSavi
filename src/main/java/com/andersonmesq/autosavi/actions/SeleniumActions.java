package com.andersonmesq.autosavi.actions;

import com.andersonmesq.autosavi.utils.LogMarkers;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class SeleniumActions {
    private static final Logger log = LoggerFactory.getLogger(SeleniumActions.class);
    private String mensagemPopUp;

    public static void startDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void waitForDomUpdate(WebDriver driver, By observer) {
        WebElement element = driver.findElement(observer);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.stalenessOf(element));
    }

    public void popUpTratament(WebDriver driver, By textPopUp, By buttonPopUp) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        for (int i = 1; i <= 5; i++) {
            LogMarkers.user(log, "Tentativa {}° de tratar o popup.", i);
            try {
                List<WebElement> mensagens = wait.until(driver1 -> {
                    List<WebElement> els = driver.findElements(textPopUp);
                    for (WebElement el : els) {
                        String texto = el.getText();

                        if (!texto.isBlank()) {
                            return els;
                        }
                    }
                    return null;
                });

                if (mensagens.isEmpty()) {
                    Thread.sleep(1000);
                    continue;
                }
                for (WebElement msg : mensagens) {
                    String msgText = msg.getText();
                    if (!msgText.isEmpty()) {
                        LogMarkers.user(log, "Mensagem lida: {}", msgText);
                        setMensagemPopUp(msgText);
                        break;
                    }
                }
                WebElement botaoFechar = wait.until(ExpectedConditions.elementToBeClickable(buttonPopUp));
                botaoFechar.click();
                return;
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                LogMarkers.user(log, "Erro ao tratar popup: ", e.getMessage());
                log.debug("Erro ao tratar popup: ", e);
            }
        }
    }

    public void pressImput(WebDriver driver, By formId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement imput = wait.until(ExpectedConditions.elementToBeClickable(formId));
        try {
            imput.click();
        } catch (Exception e) {
            LogMarkers.user(log, "Erro ao clicar no campo: ", e.getMessage());
            log.debug("Erro ao tentar clicar no formId={}", formId, e);
        }
    }

    public void selectBox(WebDriver driver, By formId, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(formId));
        Select select = new Select(element);
        try {
            select.selectByValue(value);
        } catch (Exception e) {
            LogMarkers.user(log, "Erro ao acessar caixa de seleção: ", e.getMessage());
            log.debug("Erro ao tentar acessar o formId={}", formId, e);
        }
    }

    public void writeText(WebDriver driver, By formId, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(formId));
        try {
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            LogMarkers.user(log, "Erro ao tentar escrever texto: ", e.getMessage());
            log.debug("Erro ao tentar acessar ou escrever, formId={}", formId, e);
        }
    }

    public void setMensagemPopUp(String mensagemPopUp) {
        this.mensagemPopUp = mensagemPopUp;
    }

    public String getMensagemPopUp() {
        return mensagemPopUp;
    }
}