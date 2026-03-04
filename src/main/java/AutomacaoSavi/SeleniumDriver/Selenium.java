package AutomacaoSavi.SeleniumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.logging.Logger;

public class Selenium {
    private static final Logger logger = Logger.getLogger(Selenium.class.getName());
    private String mensagemPopUp;

    public boolean tratarPopUp(WebDriver driver) {
        try {
            List<WebElement> mensagens = driver.findElements(By.cssSelector("span.ui-messages-warn-summary, span.ui-messages-info-summary"));
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

            WebElement botaoFechar = driver.findElement(By.cssSelector("button.btn.btn-info"));
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