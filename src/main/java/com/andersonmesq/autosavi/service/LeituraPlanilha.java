package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.actions.SeleniumActions;
import org.apache.poi.ss.usermodel.*;
import java.util.logging.Logger;

public class LeituraPlanilha {
    private static final Logger logger = Logger.getLogger(LeituraPlanilha.class.getName());

    public static String filePath() {
        return "C:\\Users\\ander\\IdeaProjects\\ProjetoAutoSavi\\Planilha modelo.xlsx";
    }

    public boolean verificacaoCadastro(SeleniumActions selenium) {
        String mensagem = selenium.getMensagemPopUp();
        System.out.println("Mensagem do metodo verificação cadastro: " + mensagem);
        return mensagem.contains("Honorário Médico processado com sucesso");
    }

    public void setCellCadastro(SeleniumActions selenium, Sheet sheet, int i) {
        Row row = sheet.getRow(i);
        if (row == null) return;

        Cell primeiraCelula = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (primeiraCelula == null || primeiraCelula.getStringCellValue().isBlank()) return;

        Cell valorCadastro = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        if (verificacaoCadastro(selenium)) {
            valorCadastro.setCellValue("OK");
        } else {
            valorCadastro.setCellValue("ERRO");
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
