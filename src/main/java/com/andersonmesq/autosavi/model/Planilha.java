package com.andersonmesq.autosavi.model;

import com.andersonmesq.autosavi.enums.TipoAtoData;
import com.andersonmesq.autosavi.enums.ViaAcessoData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Planilha {
    private static final Logger log = LoggerFactory.getLogger(Planilha.class);
    private String senha;
    private int quantidade;
    private TipoAtoData tipoAto;
    private LocalDate data;
    private String hora;
    private ViaAcessoData viaAcesso;
    private BigDecimal valor;
    private String observacao;

    public void planilhaReport(){
        log.debug(
                "Senha: {}\nQuantidade: {}\nTipoAto: {}\nData: {}\nHora: {}\nViaAcesso: {}\nValor: {}\nObservacao: {}",
                getSenha(),
                getQuantidade(),
                getTipoAto(),
                getData(),
                getHora(),
                getViaAcesso(),
                getValor(),
                getObservacao()
        );
    }

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

    public TipoAtoData getTipoAto() {
        return tipoAto;
    }

    public void setTipoAto(TipoAtoData tipoAto) {
        this.tipoAto = tipoAto;
    }

    public void setViaAcesso(ViaAcessoData viaAcesso) {
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

    public ViaAcessoData getViaAcesso() {
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