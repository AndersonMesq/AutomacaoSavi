package AutomacaoSavi.Automacao;

import AutomacaoSavi.Planilha.Planilha;
import AutomacaoSavi.SeleniumDriver.Selenium;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;

public class Automacao {
    private final Robot robot;
    private int numeroDeSetasPres;
    private int numeroDeSetasAto;
    private int numeroDeSetasVia;

    public Automacao(Robot robot) {
        this.robot = robot;
    }

    public void pressDown(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
    }

    public void shortDelay() {
        robot.delay(1000);
    }

    public void delayInicial() {
        robot.delay(5000); // Delay para começar
    }

    public void autoKeybord(Planilha planilha, Savi savi, WebDriver driver, Selenium selenium) {
        //1 - Campo senha
        selenium.writeText(driver, savi.getCampoSenha(), planilha.getSenha());
        //2 - Pesquisar senha
        selenium.pressImput(driver, savi.getImputSenha());
        shortDelay();
        //3 - Campo quantidade
        selenium.writeText(driver, savi.getCampoQuantidade(), String.valueOf(planilha.getQuantidade()));
        //4 - Campo prestador
        selenium.pressImput(driver, savi.getCampoPrestador());
        pressDown(this.numeroDeSetasPres);
        shortDelay();
        //5 - Campo tipo de ato
        //Solução temporaria para o problema do tipo do ato errado (Reset na força)
        selenium.pressImput(driver, savi.getCampoTipoAto());
        for (int i = 0; i < 14; i++) {
            robot.keyPress(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_UP);
        }
        this.numeroDeSetasAto = planilha.getNumeroTipoAto();
        if (this.numeroDeSetasAto != 0) {
            pressDown(numeroDeSetasAto);
        } else {
            System.out.println("Erro ao ler o tipo de ato");
        }
        shortDelay();
        //6 - Campo data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        selenium.writeText(driver, savi.getCampoData(), planilha.getData().format(formatter));
        //7 - Campo hora
        selenium.writeText(driver, savi.getCampoHora(), planilha.getHora());
        //8 - Campo via de acesso
        selenium.pressImput(driver, savi.getCampoViaAcesso());
        this.numeroDeSetasVia = planilha.getNumeroViaAcesso();
        if (this.numeroDeSetasVia != 0) {
            pressDown(this.numeroDeSetasVia);
        }
        //9 - Campo valor
        selenium.writeText(driver, savi.getCampoValor(), planilha.getValor().toString());
        //10 - Campo observacao
        selenium.writeText(driver, savi.getCampoOBS(), planilha.getObservacao());
        //10 - Gravar guia
        selenium.pressImput(driver, savi.getImputGravar());
        shortDelay();
        //11 - tratar pop-up
        int tentativas = 0;
        int limiteTentativas = 5;
        boolean deteccao;
        while (tentativas < limiteTentativas) {
            tentativas++;
            System.out.println(tentativas + "° Tentativa de tratamento do popUp");

            deteccao = selenium.tratarPopUp(driver);
            if (deteccao) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getNumeroDeSetasPres() {
        return numeroDeSetasPres;
    }

    public void setNumeroDeSetasPres(int numeroDeSetasPres) {
        this.numeroDeSetasPres = numeroDeSetasPres;
    }

    public int getNumeroDeSetasAto() {
        return numeroDeSetasAto;
    }

    public int getNumeroDeSetasVia() {
        return numeroDeSetasVia;
    }
}