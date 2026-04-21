package com.andersonmesq.autosavi.model;

public class Prestador {
    private String nome;
    private String value;

    public Prestador(String nome, String value) {
        this.nome = nome;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return nome;
    }
}