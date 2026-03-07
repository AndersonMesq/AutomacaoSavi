package com.andersonmesq.autosavi.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.logging.Logger;

public class seleniumActions {
    private static final Logger logger = Logger.getLogger(seleniumActions.class.getName());
    private String mensagemPopUp;

    public boolean tratarPopUp(WebDriver driver, By textPopUp, By buttonPopUp) {
        try {
            List<WebElement> mensagens = driver.findElements(textPopUp);
            if (mensagens.isEmpty()) {
                return false;
            }

            for (WebElement msg : mensagens) {
                String texto = msg.getText();
                if (!texto.isEmpty()) {
                    System.out.println("Mensagem lida: " + texto);
                    setMensagemPopUp(texto);
                    break;
                }
            }

            WebElement botaoFechar = driver.findElement(buttonPopUp);
            botaoFechar.click();
            return true;

        } catch (TimeoutException e) {
            System.out.println("Nenhum pop-up detectado.");
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao tratar pop-up: " + e.getMessage());
            return false;
        }
    }

    public void pressImput(WebDriver driver, By formId) {
        driver.findElement(formId).click();
    }

    public void selectBox(WebDriver driver, By formId, String value){
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