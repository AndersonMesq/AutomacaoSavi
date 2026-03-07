package com.andersonmesq.autosavi.utils;

import com.andersonmesq.autosavi.automation.automacao;
import com.andersonmesq.autosavi.model.planilha;

import java.time.format.DateTimeFormatter;

public class relatorio {

    public static void planilhaReport(planilha planilha) {
        System.out.println("---------------Relatorio da planilha---------------");
        System.out.println("Senha foi armazenada(" + planilha.getSenha() + ")");
        System.out.println("Quantidade foi armazenada(" + planilha.getQuantidade() + ")");
        System.out.println("Tipo do ato foi armazenado(" + planilha.getTipoAto() + ")");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Data foi armazenada(" + planilha.getData().format(formatter) + ")");
        System.out.println("Hora foi armazenada(" + planilha.getHora() + ")");
        System.out.println("Via de acesso foi armazenada(" + planilha.getViaAcesso() + ")");
        System.out.println("Valor foi armazenado(" + planilha.getValor() + ")");
        System.out.println("OBS armazenado: " + planilha.getObservacao());
        System.out.println("---------------------------------------------------");
    }

    public static void autoReport(planilha planilha, automacao automacao){
        System.out.println("///////////////////RELATORIO AUTO///////////////////");
        System.out.println("Senha: " + planilha.getSenha());
        System.out.println("Quantidade: " + planilha.getQuantidade());
        System.out.println("Tipo do ato: " + planilha.getTipoAto());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Data: " + planilha.getData().format(formatter));
        System.out.println("Hora: " + planilha.getHora());
        System.out.println("Via de acesso: " + planilha.getViaAcesso());
        System.out.println("Valor: " + planilha.getValor());
        System.out.println("OBS: " + planilha.getObservacao());
        System.out.println("////////////////////////////////////////////////////");
    }
}
