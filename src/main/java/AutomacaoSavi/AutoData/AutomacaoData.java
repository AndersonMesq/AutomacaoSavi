package AutomacaoSavi.AutoData;

import AutomacaoSavi.Automacao.Automacao;
import AutomacaoSavi.Planilha.Planilha;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AutomacaoData {
    public void prestadorDefinition(Automacao automacao) {
        //Definindo qual prestador sera escolhido
        Scanner sequenciaPrestador = new Scanner(System.in);
        System.out.println("Verifique em seu campo de cadastro de guias no Savi " +
                "em qual posição esta o prestador que deseja selecionar " +
                "(Ex: Hapvida é o terceiro prestador listado. Resposta: 3)");
        System.out.print("Sua resposta: ");

        automacao.setNumeroDeSetasPres(sequenciaPrestador.nextInt());
    }

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
                planilha.setTipoAto(valorTipoAto);
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
                planilha.setViaAcesso(valorViaAcesso);
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

    public void setNumeroTipoAto(Planilha planilha) {
        Map<String, Integer> tipoAtoMap = new HashMap<>();
        tipoAtoMap.put("A.ESP", 1);
        tipoAtoMap.put("ANEST", 2);
        tipoAtoMap.put("ASS.RN.BER", 3);
        tipoAtoMap.put("ASS.RN.PARTO", 4);
        tipoAtoMap.put("AUX.ANEST", 5);
        tipoAtoMap.put("CIR/OBST", 6);
        tipoAtoMap.put("CONS/HON", 7);
        tipoAtoMap.put("D.AUX", 8);
        tipoAtoMap.put("PACOTE", 9);
        tipoAtoMap.put("PARECER", 10);
        tipoAtoMap.put("PERF", 11);
        tipoAtoMap.put("1AUX", 12);
        tipoAtoMap.put("2AUX", 13);
        tipoAtoMap.put("3AUX", 14);

        planilha.setNumeroTipoAto(tipoAtoMap.getOrDefault(planilha.getTipoAto(), 0));
    }

    public void setNumeroViaAcesso(Planilha planilha) {
        Map<String, Integer> viaAcessoMap = new HashMap<>();
        viaAcessoMap.put("M", 1);
        viaAcessoMap.put("D", 2);

        planilha.setNumeroViaAcesso(viaAcessoMap.getOrDefault(planilha.getViaAcesso(), 0));
    }
}
