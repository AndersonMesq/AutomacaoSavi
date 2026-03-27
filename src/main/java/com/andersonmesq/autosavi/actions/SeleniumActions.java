package com.andersonmesq.autosavi.actions;

import com.andersonmesq.autosavi.pages.SaviPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

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

    public void tratarPopUp(WebDriver driver, By textPopUp, By buttonPopUp) {
        int tentativas = 1;
        while (tentativas <= 5) {
            System.out.println(tentativas + "° Tentativa de tratamento do popUp");
            List<WebElement> mensagens = driver.findElements(textPopUp);
            if (mensagens.isEmpty()) {
                tentativas++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            for (WebElement msg : mensagens) {
                String msgText = msg.getText();
                if (!msgText.isEmpty()) {
                    System.out.println("Mensagem lida: " + msgText);
                    setMensagemPopUp(msgText);
                    break;
                }
            }
            WebElement botaoFechar = driver.findElement(buttonPopUp);
            botaoFechar.click();
            return;
        }
    }

    public boolean isTelaCadastro(WebDriver driver, SaviPage saviPage) {
        try {
            driver.findElement(saviPage.getCampoPrestador());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void pressImput(WebDriver driver, By formId) {
        driver.findElement(formId).click();
    }

    public void selectBox(WebDriver driver, By formId, String value) {
        Select select = new Select(driver.findElement(formId));
        select.selectByValue(value);
    }

    public void writeText(WebDriver driver, By formId, String text) {
        WebElement form = driver.findElement(formId);
        form.clear();
        form.sendKeys(text);
    }

    public void setMensagemPopUp(String mensagemPopUp) {
        this.mensagemPopUp = mensagemPopUp;
    }

    public String getMensagemPopUp() {
        return mensagemPopUp;
    }
}