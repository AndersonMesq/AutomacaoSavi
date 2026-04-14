package com.andersonmesq.autosavi.context;

import com.andersonmesq.autosavi.service.AutoMaster;

import java.io.File;

public class AutomationContext {
    private File arquivo;

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }
}
