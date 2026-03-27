package com.andersonmesq.autosavi.automation;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.pages.SaviPage;
import com.andersonmesq.autosavi.strategy.SiteStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SaviStrategy implements SiteStrategy {
    private final SeleniumActions selenium;
    private final DriverFactory driverFactory;
    private WebDriver driver;
    private SaviPage saviPage;

    public SaviStrategy(SeleniumActions selenium, DriverFactory driverFactory) {
        this.selenium = selenium;
        this.driverFactory = driverFactory;
    }

    @Override
    public List<Prestador> loadPrestadores() {
        Map<String, String> mapa = extractPrestadores(driverFactory, saviPage);

        List<Prestador> lista = new ArrayList<>();

        for (Map.Entry<String, String> entry : mapa.entrySet()) {
            lista.add(new Prestador(entry.getValue(), entry.getKey()));
        }

        return lista;
    }

    public Map<String, String> extractPrestadores(DriverFactory driverFactory, SaviPage saviPage) {
        driver = driverFactory.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(saviPage.getCampoPrestador()));

        Select select = new Select(selectElement);

        Map<String, String> prestadores = new LinkedHashMap<>();

        for (WebElement option : select.getOptions()) {
            String value = option.getAttribute("value");
            String nome = option.getText();
            if (value != null && !value.isBlank()) {
                prestadores.put(value, nome);
            }
        }
        return prestadores;
    }

    @Override
    public void startStrategy(Planilha planilha, Prestador prestador) {
        //1 - Campo senha
        selenium.writeText(driver, saviPage.getCampoSenha(), planilha.getSenha());
        //2 - Pesquisar senha
        selenium.pressImput(driver, saviPage.getImputSenha());
        //3 - Campo quantidade
        selenium.writeText(driver, saviPage.getCampoQuantidade(), String.valueOf(planilha.getQuantidade()));
        //4 - Campo prestador
        selenium.pressImput(driver, saviPage.getCampoPrestador());
        selenium.selectBox(driver, saviPage.getCampoPrestador(), prestador.getValue());
        //5 - Campo tipo de ato
        selenium.selectBox(driver, saviPage.getCampoTipoAto(), planilha.getTipoAto().getValueHtml());
        //6 - Campo data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        selenium.writeText(driver, saviPage.getCampoData(), planilha.getData().format(formatter));
        //7 - Campo hora
        selenium.writeText(driver, saviPage.getCampoHora(), planilha.getHora());
        //8 - Campo via de acesso
        selenium.selectBox(driver, saviPage.getCampoViaAcesso(), planilha.getViaAcesso().getCodigo());
        //9 - Campo valor
        selenium.writeText(driver, saviPage.getCampoValor(), planilha.getValor().toString());
        //10 - Campo observacao
        selenium.writeText(driver, saviPage.getCampoOBS(), planilha.getObservacao());
        //10 - Gravar guia
        selenium.pressImput(driver, saviPage.getImputGravar());
        //11 - tratar pop-up
        selenium.tratarPopUp(driver, saviPage.getPopUptext(), saviPage.getPopUpButton());
    }
}
