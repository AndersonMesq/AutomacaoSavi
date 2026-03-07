package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.selenium.Selenium;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LeituraPlanilha {
    private static final Logger logger = Logger.getLogger(LeituraPlanilha.class.getName());

    public static String filePath() {
        return "C:\\Users\\ander\\IdeaProjects\\ProjetoAutoSavi\\Planilha modelo.xlsx";
    }

    public void sheetValidation() {
        try (FileInputStream fis = new FileInputStream(filePath());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row cabecalho = sheet.getRow(0);

            java.util.List<String> colunasObrigatorias = Arrays.asList("senha", "quantidade", "tipoAto", "data",
                    "hora", "viaAcesso", "valor", "OBS", "mensagem", "cadastro");
            Map<String, Integer> colunas = new HashMap<>();

            for (String coluna : colunasObrigatorias) {
                for (Cell cell : cabecalho) {
                    if (cell.getStringCellValue().trim().equalsIgnoreCase(coluna)) {
                        colunas.put(coluna, cell.getColumnIndex());
                    }
                }
            }

            if (colunas.size() < colunasObrigatorias.size()) {
                throw new RuntimeException("Colunas não encontrada ou com nome incorreto");
            } else {
                System.out.println("Colunas validadas com sucesso\n");
            }
        } catch (IOException e) {
            logger.severe("Erro ao tentar validar planilha" + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Detalhes do erro: ", e);
        }
    }

    public int verificacaoCadastro(Selenium selenium) {
        String mensagem = selenium.getMensagemPopUp();
        return mensagem.contains("Honorário Médico processado com sucesso") ? 1 : 0;
    }

    public void setCellCadastro(Selenium selenium, Sheet sheet, int i) {
        Row row = sheet.getRow(i);
        if (row == null) return;

        Cell primeiraCelula = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (primeiraCelula == null || primeiraCelula.getStringCellValue().isBlank()) return;

        Cell valorCadastro = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        if (verificacaoCadastro(selenium) == 1) {
            valorCadastro.setCellValue("OK");
        } else {
            valorCadastro.setCellValue("ERRO");
        }
    }

    public void setCellMensagem(Selenium selenium, Sheet sheet, int i) {
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
