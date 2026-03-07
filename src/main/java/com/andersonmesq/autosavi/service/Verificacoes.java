package com.andersonmesq.autosavi.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Verificacoes {
    public void soma(WebDriver driver){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pressione ENTER para iniciar a extração...");
        scanner.nextLine();

        try {
            List<WebElement> elementos = driver.findElements(By.xpath(
                    "//td[contains(@style,'background-color:#cdffcd') and contains(@class,'text-center')]"
            ));

            List<Double> valores = new ArrayList<>();

            for (WebElement elemento : elementos) {
                String texto = elemento.getText().trim();

                if (!texto.isEmpty()) {
                    String valorFormatado = texto.replace(",", "");
                    try {
                        double numero = Double.parseDouble(valorFormatado);
                        valores.add(numero);
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido ignorado: " + texto);
                    }
                }
            }

            double somaTotal = valores.stream().mapToDouble(Double::doubleValue).sum();

            System.out.println("\nValores encontrados: " + valores);
            System.out.println("Soma total: " + somaTotal);

        } catch (Exception e) {
            System.out.println("Erro ao extrair valores: " + e.getMessage());
        }
    }
}
