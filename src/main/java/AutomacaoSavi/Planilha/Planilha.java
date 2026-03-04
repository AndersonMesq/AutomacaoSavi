package AutomacaoSavi.Planilha;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Planilha {
    private String senha;
    private int quantidade;
    private String tipoAto;
    private int numeroTipoAto;
    private LocalDate data;
    private String hora;
    private String viaAcesso;
    private int numeroViaAcesso;
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

    public String getTipoAto() {
        return tipoAto;
    }

    public void setTipoAto(String tipoAto) {
        this.tipoAto = tipoAto;
    }

    public int getNumeroTipoAto() {
        return numeroTipoAto;
    }

    public void setNumeroTipoAto(int numeroTipoAto) {
        this.numeroTipoAto = numeroTipoAto;
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

    public String getViaAcesso() {
        return viaAcesso;
    }

    public void setViaAcesso(String viaAcesso) {
        this.viaAcesso = viaAcesso;
    }

    public int getNumeroViaAcesso() {
        return numeroViaAcesso;
    }

    public void setNumeroViaAcesso(int numeroViaAcesso) {
        this.numeroViaAcesso = numeroViaAcesso;
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