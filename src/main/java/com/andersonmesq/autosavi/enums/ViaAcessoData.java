package com.andersonmesq.autosavi.enums;

public enum ViaAcessoData {
    MESMA("M"),
    DIFERENTE("D");

    private final String codigo;

    ViaAcessoData(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static ViaAcessoData fromExcel(String codigo) {
        for (ViaAcessoData tipo : values()) {
            if (tipo.codigo.equalsIgnoreCase(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo inválido: " + codigo);
    }
}
