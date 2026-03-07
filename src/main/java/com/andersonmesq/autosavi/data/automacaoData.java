package com.andersonmesq.autosavi.data;

import com.andersonmesq.autosavi.automation.automacao;
import com.andersonmesq.autosavi.model.planilha;
import com.andersonmesq.autosavi.enums.tipoAtoData;
import com.andersonmesq.autosavi.enums.viaAcessoData;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class automacaoData {
    public void prestadorDefinition(automacao automacao) {
        //Definindo qual prestador sera escolhido
        Scanner sequenciaPrestador = new Scanner(System.in);
        System.out.println("Verifique em seu campo de cadastro de guias no Savi " +
                "em qual posição esta o prestador que deseja selecionar " +
                "(Ex: Hapvida é o terceiro prestador listado. Resposta: 3)");
        System.out.print("Sua resposta: ");

        automacao.setNumeroDeSetasPres(sequenciaPrestador.nextInt());
    }

    public void planilhaSetters(planilha planilha, DataFormatter formatter, String colunaNome, Cell cell) {
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
                planilha.setTipoAto(tipoAtoData.fromExcel(valorTipoAto));
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
                planilha.setViaAcesso(viaAcessoData.fromExcel(valorViaAcesso));
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
