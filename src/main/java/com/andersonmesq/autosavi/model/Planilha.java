package com.andersonmesq.autosavi.model;

import com.andersonmesq.autosavi.enums.tipoAtoData;
import com.andersonmesq.autosavi.enums.viaAcessoData;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Planilha {
    private String senha;
    private int quantidade;
    private tipoAtoData tipoAto;
    private LocalDate data;
    private String hora;
    private viaAcessoData viaAcesso;
    private BigDecimal valor;
    private String observacao;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public tipoAtoData getTipoAto() {
        return tipoAto;
    }

    public void setTipoAto(tipoAtoData tipoAto) {
        this.tipoAto = tipoAto;
    }

    public void setViaAcesso(viaAcessoData viaAcesso) {
        this.viaAcesso = viaAcesso;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public viaAcessoData getViaAcesso() {
        return viaAcesso;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}