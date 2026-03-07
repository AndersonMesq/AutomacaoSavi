package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.data.AutomacaoData;
import com.andersonmesq.autosavi.utils.Relatorio;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.driver.DriverFactory;
import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.automation.Automacao;
import com.andersonmesq.autosavi.pages.SaviCadastro;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AutoMaster {
    private static final Logger logger = Logger.getLogger(AutoMaster.class.getName());
    private final Planilha planilha;
    private final SaviCadastro savi;
    private final LeituraPlanilha leituraPlanilha;
    private final AutomacaoData automacaoData;
    private final Automacao automacao;
    private final DriverFactory driverS;
    private final SeleniumActions selenium;

    public AutoMaster(Planilha planilha, SaviCadastro savi, LeituraPlanilha leituraPlanilha,
                      AutomacaoData automacaoData, Automacao automacao,
                      DriverFactory driverS, SeleniumActions selenium) {
        this.planilha = planilha;
        this.savi = savi;
        this.leituraPlanilha = leituraPlanilha;
        this.automacaoData = automacaoData;
        this.automacao = automacao;
        this.driverS = driverS;
        this.selenium = selenium;
    }

    public void autoStart() {
        leituraPlanilha.sheetValidation();
        driverS.openSavi();
        WebDriver driver = driverS.getDriver();
        automacaoData.prestadorDefinition(automacao);
        try (FileInputStream fis = new FileInputStream(LeituraPlanilha.filePath())) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Row primeiraLinha = sheet.getRow(0);
            DataFormatter formatter = new DataFormatter();
            Map<String, Integer> colunas = new HashMap<>();

            for (Cell cell : primeiraLinha) {
                String cabecalho = formatter.formatCellValue(cell);
                colunas.put(cabecalho, cell.getColumnIndex());
            }

            linhaLoop:
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row linha = sheet.getRow(i);
                if (linha == null) {
                    break;
                }

                if (LeituraPlanilha.checkCadastro(sheet, formatter, i)) {
                    System.out.println("**************************\n" +
                            "Linha " + i + " com cadastro OK\n" +
                            "**************************"
                    );
                    continue;
                }
                for (Map.Entry<String, Integer> entry : colunas.entrySet()) {
                    String colunaNome = entry.getKey();
                    int indice = entry.getValue();
                    Cell cell = linha.getCell(indice, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    if (colunaNome.equals("senha") && formatter.formatCellValue(cell).isEmpty()) {
                        break linhaLoop;
                    }
                    automacaoData.planilhaSetters(planilha, formatter, colunaNome, cell);
                }
                Relatorio.planilhaReport(planilha);
                automacao.delayInicial();
                automacao.autoKeybord(planilha, savi, driver, selenium);
                Relatorio.autoReport(planilha, automacao);
                leituraPlanilha.setCellMensagem(selenium, sheet, i);
                leituraPlanilha.setCellCadastro(selenium, sheet, i);
            }

            try (FileOutputStream fos = new FileOutputStream(LeituraPlanilha.filePath())) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            logger.severe("Erro ao tentar executar automação: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Detalhes do erro: ", e);
        }
    }
}