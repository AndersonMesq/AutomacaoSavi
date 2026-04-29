package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeituraPlanilha {
    private static final Logger log = LoggerFactory.getLogger(LeituraPlanilha.class);

    public void setCellCadastro(SeleniumActions selenium, Sheet sheet, int i) {
        Row row = sheet.getRow(i);
        String mensagem = selenium.getMensagemPopUp();
        if (row == null) return;

        Cell primeiraCelula = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (primeiraCelula == null || primeiraCelula.getStringCellValue().isBlank()) return;

        Cell valorCadastro = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        if (mensagem.contains("Honorário Médico processado com sucesso")) {
            valorCadastro.setCellValue("OK");
            log.debug("Linha {} registrada como cadastro OK", i);
        } else {
            valorCadastro.setCellValue("ERRO");
            log.debug("Linha {} registrada como cadastro ERRO", i);
        }
    }

    public void setCellMensagem(SeleniumActions selenium, Sheet sheet, int i) {
        Row row = sheet.getRow(i);
        if (row == null) return;

        Cell valorMensagem = row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        valorMensagem.setCellValue(selenium.getMensagemPopUp());
    }

    public static boolean checkCadastro(Sheet sheet, DataFormatter formatter, int i) {
        Row row = sheet.getRow(i);

        Cell valorCadastrado = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        if (valorCadastrado == null) return false;

        String statusCadastrado = formatter.formatCellValue(valorCadastrado);

        return statusCadastrado.trim().equalsIgnoreCase("OK");
    }
}
