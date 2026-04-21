package com.andersonmesq.autosavi.strategy;

import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.model.Prestador;

import java.util.List;

public interface SiteStrategy {
    void startStrategy(Planilha planilha, Prestador prestador);

    List<Prestador> loadPrestadores();
}
