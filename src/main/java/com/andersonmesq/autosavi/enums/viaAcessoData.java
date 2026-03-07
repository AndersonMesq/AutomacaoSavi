package com.andersonmesq.autosavi.enums;

public enum viaAcessoData {
    MESMA("M"),
    DIFERENTE("D");

    private final String codigo;

    viaAcessoData(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static viaAcessoData fromExcel(String codigo) {
        for (viaAcessoData tipo : values()) {
            if (tipo.codigo.equalsIgnoreCase(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo inválido: " + codigo);
    }
}
