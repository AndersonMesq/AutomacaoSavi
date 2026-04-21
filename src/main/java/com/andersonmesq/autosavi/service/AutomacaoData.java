package com.andersonmesq.autosavi.service;

import com.andersonmesq.autosavi.model.Planilha;
import com.andersonmesq.autosavi.enums.TipoAtoData;
import com.andersonmesq.autosavi.enums.ViaAcessoData;
import com.andersonmesq.autosavi.utils.LogMarkers;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AutomacaoData {
    private static final Logger log = LoggerFactory.getLogger(AutomacaoData.class);

    public void planilhaSetters(Planilha planilha, DataFormatter formatter, String colunaNome, Cell cell) {
        switch (colunaNome) {
            case "senha" -> {
                String valorSenha = formatter.formatCellValue(cell);
                planilha.setSenha(valorSenha);
            }

            case "quantidade" -> {
                String valorQuantidade = formatter.formatCellValue(cell);
                planilha.setQuantidade(Integer.parseInt(valorQuantidade));
            }

            case "tipoAto" -> {
                String valorTipoAto = formatter.formatCellValue(cell);
                planilha.setTipoAto(TipoAtoData.fromExcel(valorTipoAto));
            }

            case "data" -> {
                if (cell.getCellType() == CellType.NUMERIC) {
                    LocalDate data = cell.getLocalDateTimeCellValue().toLocalDate();
                    planilha.setData(data);
                }
            }

            case "hora" -> {
                String valorHora = formatter.formatCellValue(cell);
                valorHora = valorHora.replaceAll("[^0-9]", "");
                planilha.setHora(valorHora);
            }

            case "viaAcesso" -> {
                String valorViaAcesso = formatter.formatCellValue(cell);
                planilha.setViaAcesso(ViaAcessoData.fromExcel(valorViaAcesso));
            }

            case "valor" -> {
                if (cell.getCellType() == CellType.NUMERIC) {
                    planilha.setValor(
                            BigDecimal.valueOf(cell.getNumericCellValue())
                    );
                } else {
                    String valorTexto = formatter.formatCellValue(cell)
                            .replace(".", "")
                            .replace(",", ".");

                    planilha.setValor(new BigDecimal(valorTexto));
                }
            }

            case "OBS" -> {
                String valorObservacoes = formatter.formatCellValue(cell);
                planilha.setObservacao(valorObservacoes);
            }
        }
    }
}
