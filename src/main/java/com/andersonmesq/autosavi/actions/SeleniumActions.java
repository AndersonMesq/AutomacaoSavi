package com.andersonmesq.autosavi.actions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class SeleniumActions {
    private static final Logger logger = Logger.getLogger(SeleniumActions.class.getName());
    private String mensagemPopUp;

    public static void startDelay() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void waitForDomUpdate(WebDriver driver, By observer) {
        WebElement element = driver.findElement(observer);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.stalenessOf(element));
    }

    public void tratarPopUp(WebDriver driver, By textPopUp, By buttonPopUp) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        for (int i = 1; i <= 5; i++) {
            System.out.println("Tentativa " + i + " de tratamento do popUp");
            try {
                List<WebElement> mensagens = wait.until(driver1 -> {
                    List<WebElement> els = driver.findElements(textPopUp);

                    for (WebElement el : els) {
                        String texto = el.getText();

                        if (texto != null && !texto.isBlank()) {
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
                    System.out.println("WebElement msg: " + msg);
                    System.out.println("mensagens: " + mensagens);
                    String msgText = msg.getText();
                    System.out.println("msgText: " + msgText);
                    if (!msgText.isEmpty()) {
                        System.out.println("Mensagem lida: " + msgText);
                        setMensagemPopUp(msgText);
                        Thread.sleep(200);
                        break;
                    }
                }
                WebElement botaoFechar = wait.until(
                        ExpectedConditions.elementToBeClickable(buttonPopUp)
                );
                botaoFechar.click();
                return;
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void pressImput(WebDriver driver, By formId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement imput = wait.until(ExpectedConditions.elementToBeClickable(formId));
        imput.click();
    }

    public void selectBox(WebDriver driver, By formId, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(formId));
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void writeText(WebDriver driver, By formId, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(formId));
        element.clear();
        element.sendKeys(text);
    }

    public void setMensagemPopUp(String mensagemPopUp) {
        this.mensagemPopUp = mensagemPopUp;
    }

    public String getMensagemPopUp() {
        return mensagemPopUp;
    }
}