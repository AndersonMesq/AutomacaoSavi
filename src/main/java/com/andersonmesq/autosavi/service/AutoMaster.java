package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.context.AutomationContext;
import com.andersonmesq.autosavi.factory.AppFactory;
import com.andersonmesq.autosavi.model.Prestador;
import com.andersonmesq.autosavi.strategy.SiteStrategy;
import com.andersonmesq.autosavi.controller.AutomationController;
import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.actions.SeleniumActions;
import com.andersonmesq.autosavi.utils.LogMarkers;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.andersonmesq.autosavi.enums.AutomationState.*;

public class AutoMaster {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AutoMaster.class);
    private final Planilha planilha;
    private final LeituraPlanilha leituraPlanilha;
    private final AutomacaoData automacaoData;
    private final SeleniumActions seleniumActions;
    private AutomationController controller;

    public AutoMaster(Planilha planilha, LeituraPlanilha leituraPlanilha, AutomacaoData automacaoData, SeleniumActions seleniumActions) {
        this.planilha = planilha;
        this.leituraPlanilha = leituraPlanilha;
        this.automacaoData = automacaoData;
        this.seleniumActions = seleniumActions;
    }

    public void autoStart(SiteStrategy strategy, Prestador prestador, AutomationContext automationContext) {
        controller = AppFactory.getInstance().getAutomationController();
        controller.checkAutoState();
        try (FileInputStream fis = new FileInputStream(automationContext.getArquivo())) {
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
                controller.checkAutoState();
                Row linha = sheet.getRow(i);
                if (linha == null) {
                    break;
                }
                if (LeituraPlanilha.checkCadastro(sheet, formatter, i)) {
                    LogMarkers.user(log, "**************************\nLinha {} com cadastro OK\n**************************", i);
                    continue;
                }
                for (Map.Entry<String, Integer> entry : colunas.entrySet()) {
                    controller.checkAutoState();
                    String colunaNome = entry.getKey();
                    int indice = entry.getValue();
                    Cell cell = linha.getCell(indice, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    if (colunaNome.equals("senha") && formatter.formatCellValue(cell).isEmpty()) {
                        break linhaLoop;
                    }
                    controller.checkAutoState();
                    automacaoData.planilhaSetters(planilha, formatter, colunaNome, cell);
                }
                LogMarkers.user(log, "Senha {} em cadastro", planilha.getSenha());
                planilha.planilhaReport();
                SeleniumActions.startDelay();
                controller.checkAutoState();
                strategy.startStrategy(planilha, prestador);
                planilha.planilhaReport();
                leituraPlanilha.setCellMensagem(seleniumActions, sheet, i);
                leituraPlanilha.setCellCadastro(seleniumActions, sheet, i);
                LogMarkers.user(log, "------------------------");
                log.debug("------------------------");
            }
            try (FileOutputStream fos = new FileOutputStream(automationContext.getArquivo())) {
                workbook.write(fos);
            }
            controller.setState(FINISHED);
        } catch (IOException e) {
            LogMarkers.user(log, "Erro ao tentar executar automação: {}", e.getMessage());
            log.debug("Erro ao tentar executar automação: ", e);
        }
    }
}