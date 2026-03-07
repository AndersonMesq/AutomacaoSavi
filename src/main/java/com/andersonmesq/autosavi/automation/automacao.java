package com.andersonmesq.autosavi.automation;

import com.andersonmesq.autosavi.pages.saviCadastro;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.selenium.Selenium;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;

public class automacao {
    private final Robot robot;
    private int numeroDeSetasPres;

    public automacao(Robot robot) {
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

    public void longDelay() {
        robot.delay(2000);
    }

    public void delayInicial() {
        robot.delay(5000); // Delay para começar
    }

    public void autoKeybord(Planilha planilha, saviCadastro savi, WebDriver driver, Selenium selenium) {
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
        selenium.selectBox(driver, savi.getCampoTipoAto(), planilha.getTipoAto().getValueHtml());
        //6 - Campo data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        selenium.writeText(driver, savi.getCampoData(), planilha.getData().format(formatter));
        //7 - Campo hora
        selenium.writeText(driver, savi.getCampoHora(), planilha.getHora());
        //8 - Campo via de acesso
        selenium.selectBox(driver, savi.getCampoViaAcesso(), planilha.getViaAcesso().getCodigo());
        //9 - Campo valor
        selenium.writeText(driver, savi.getCampoValor(), planilha.getValor().toString());
        //10 - Campo observacao
        selenium.writeText(driver, savi.getCampoOBS(), planilha.getObservacao());
        //10 - Gravar guia
        selenium.pressImput(driver, savi.getImputGravar());
        longDelay();
        //11 - tratar pop-up
        int tentativas = 0;
        int limiteTentativas = 5;
        boolean deteccao;
        while (tentativas < limiteTentativas) {
            tentativas++;
            System.out.println(tentativas + "° Tentativa de tratamento do popUp");

            deteccao = selenium.tratarPopUp(driver, savi.getPopUptext(), savi.getPopUpButton());
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

    public void setNumeroDeSetasPres(int numeroDeSetasPres) {
        this.numeroDeSetasPres = numeroDeSetasPres;
    }
}