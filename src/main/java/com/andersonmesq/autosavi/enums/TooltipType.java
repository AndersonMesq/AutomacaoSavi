package com.andersonmesq.autosavi.enums;

public enum TooltipType {
    HOME("Voltar a seleção de site"),
    GERAR_PLANILHA("Gerar planilha modelo");

    private final String text;

    TooltipType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
