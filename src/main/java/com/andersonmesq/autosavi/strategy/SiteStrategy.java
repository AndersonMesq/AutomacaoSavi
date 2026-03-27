package com.andersonmesq.autosavi.strategy;

import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.pages.SaviPage;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public interface SiteStrategy {
    void startStrategy(Planilha planilha, Prestador prestador);

    public List<Prestador> loadPrestadores();
}
