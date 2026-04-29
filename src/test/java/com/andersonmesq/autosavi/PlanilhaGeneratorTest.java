package com.andersonmesq.autosavi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlanilhaGeneratorTest {

    private static final Random random = new Random();
    private static final int quantidadeLinhas = 30;
    private static final LocalDate dataPadrao = LocalDate.of(2026, 6, 15);
    private static final Path destino = Paths.get("target/test-output/planilha-valida.xlsx");

    @Test
    void gerarPlanilhaValida() throws Exception {
        gerarPlanilha();

        try (Workbook wb = WorkbookFactory.create(destino.toFile())) {
            Sheet sheet = wb.getSheetAt(0);

            // valida quantidade de linhas
            assertEquals(quantidadeLinhas, sheet.getLastRowNum());

            // valida conteúdo
            Row row = sheet.getRow(1);
            assertNotNull(row);
            assertTrue(row.getCell(0).getStringCellValue().matches("[A-Z]\\d{8}"));
        }
    }

    private void gerarPlanilha() throws Exception {
        Files.createDirectories(destino.getParent());

        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet("Dados");

            // 🔥 criar cabeçalho obrigatório
            criarCabecalho(sheet);

            // 🔥 criar estilo de data UMA vez
            CellStyle dateStyle = criarDateStyle(wb);

            // 🔥 preencher dados
            for (int i = 1; i <= quantidadeLinhas; i++) {
                Row row = sheet.createRow(i);
                preencherLinha(row, wb, dateStyle);
            }

            try (FileOutputStream out = new FileOutputStream(destino.toFile())) {
                wb.write(out);
            }
        }
    }

    private void criarCabecalho(Sheet sheet) {
        Row header = sheet.createRow(0);

        String[] colunas = {
                "senha", "quantidade", "tipoAto", "data",
                "hora", "viaAcesso", "valor", "OBS", "mensagem", "cadastro"
        };

        for (int i = 0; i < colunas.length; i++) {
            header.createCell(i).setCellValue(colunas[i]);
        }
    }

    private CellStyle criarDateStyle(Workbook workbook) {
        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper helper = workbook.getCreationHelper();
        dateStyle.setDataFormat(helper.createDataFormat().getFormat("dd/MM/yyyy"));
        return dateStyle;
    }

    private void preencherLinha(Row row, Workbook workbook, CellStyle dateStyle) {
        row.createCell(0).setCellValue(gerarSenha());
        row.createCell(1).setCellValue(gerarQuantidade());
        row.createCell(2).setCellValue(gerarTipoAto());

        Cell cell = row.createCell(3);
        cell.setCellValue(java.sql.Date.valueOf(dataPadrao));
        cell.setCellStyle(dateStyle);

        row.createCell(4).setCellValue(gerarHora());
        row.createCell(5).setCellValue(gerarViaAcesso());
        row.createCell(6).setCellValue(gerarValor().doubleValue());
        row.createCell(7).setCellValue("-");
        row.createCell(8).setCellValue("-");
        row.createCell(9).setCellValue("-");
    }

    private String gerarSenha() {
        char letra = (char) ('A' + random.nextInt(26));
        int numero = 10000000 + random.nextInt(90000000);
        return letra + String.valueOf(numero);
    }

    private int gerarQuantidade() {
        return random.nextInt(5) + 1;
    }

    private String gerarTipoAto() {
        String[] tipos = {
                "A.ESP", "ASS.RN.BER", "ASS.RN.PARTO", "AUX.ANEST",
                "CIR/OBST", "CONS/HON", "D.AUX", "PACOTE",
                "PARECER", "PERF", "1AUX", "2AUX", "3AUX"
        };
        return tipos[random.nextInt(tipos.length)];
    }

    private String gerarHora() {
        int h = random.nextInt(24);
        int m = random.nextInt(60);
        return String.format("%02d:%02d", h, m);
    }

    private String gerarViaAcesso() {
        String[] vias = {"U", "D", "M"};
        return vias[random.nextInt(vias.length)];
    }

    private BigDecimal gerarValor() {
        double valor = 1 + (999 * random.nextDouble());
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
    }
}