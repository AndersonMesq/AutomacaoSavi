# AutoSavi

Sistema desktop desenvolvido em Java com foco na automação de cadastros de procedimentos médicos em plataformas web complexas.

O projeto utiliza Selenium WebDriver para automação de navegação e preenchimento de formulários, Apache POI para extração de dados de planilhas .xlsx, JavaFX para interface gráfica desktop e Maven para gerenciamento de dependências e build.

---

# Objetivo

Automatizar o processo de cadastro de procedimentos médicos no sistema AutoSavi, reduzindo:

* Tempo operacional
* Erros humanos
* Repetição manual de tarefas
* Dependência de preenchimento manual em sistemas web

---

# Funcionalidades

* Interface gráfica desktop com JavaFX
* Automação web utilizando Selenium WebDriver
* Suporte a múltiplos fluxos/sites via Strategy Pattern
* Sistema de logs em interface e arquivo `.txt`
* Empacotamento em executável Windows (`.exe`)
* Prevenção de múltiplas instâncias simultâneas
* Estrutura preparada para escalabilidade futura

---

# Tecnologias Utilizadas

* Java 21
* JavaFX 21
* Apache POI
* Selenium WebDriver
* Microsoft Edge WebDriver
* Maven
* SLF4J
* Logback
* Maven Shade Plugin
* JPackage

---

# Arquitetura

O projeto segue uma arquitetura orientada a separação de responsabilidades:

```text
src/main/java/com/andersonmesq/autosavi
│
├── actions       → ações reutilizáveis do Selenium
├── automation    → estratégias de automação
├── controller    → controllers JavaFX
├── enums         → enums do sistema
├── factory       → criação e configuração de drivers
├── model         → modelos de dados
├── service       → regras de negócio e orquestração
├── utils         → utilitários gerais
```

---

# Padrões e Conceitos Aplicados

* Programação Orientada a Objetos (POO)
* Strategy Pattern
* Factory Pattern
* Separação de responsabilidades
* Automação web
* Logging estruturado
* Empacotamento desktop com JPackage

---

# Requisitos

## Java

Projeto desenvolvido utilizando:

```text
Java 21
```

Download oficial:
https://www.oracle.com/java/technologies/downloads/#java21

---

## JavaFX SDK

O JavaFX não está incluído no JDK e precisa ser instalado separadamente.

Download oficial:

https://gluonhq.com/products/javafx/

Baixe uma versão compativel com o Java 21

Após baixar:

1. Extraia o SDK
2. Renomeie a pasta para:

```text
JavaFX
```

3. Coloque em:

```text
C:\JavaFX
```

O caminho final que deve existir:

```text
C:\JavaFX\lib
```

---

# Como Executar pela IDE

## Clonar o repositório

```bash
  git clone <repositorio>
```

---

## Compilar o projeto

```bash
  mvn clean package
```

---

## Executar o `.jar`

```bash
  java --module-path "C:\JavaFX\lib" --add-modules javafx.controls,javafx.fxml -jar target/ProjetoAutoSavi-1.0-SNAPSHOT-shaded.jar
```

## Executar via terminal na IDE

```bash
  mvn javafx:run
```

---

# Como Gerar o Executável Windows (.exe)

## Gerar build

```bash
  mvn clean package
```

---

## Gerar executável com JPackage

```bash
  jpackage --name AutoSavi --input target --main-jar ProjetoAutoSavi-1.0-SNAPSHOT-shaded.jar --main-class com.andersonmesq.autosavi.Main --type exe --dest dist --java-options "--module-path C:\JavaFX\lib --add-modules javafx.controls,javafx.fxml" --icon src/main/resources/icon/logotipo-savi.ico
```

---

# Logs

O sistema utiliza Logback + SLF4J para logging.

Os logs incluem:

* Eventos de automação
* Inicialização do sistema
* Falhas de Selenium
* Problemas de driver
* Fluxos internos da aplicação

---

# Erros Mais Prováveis

* Aplicativo não abre: Java 21 ou JavaFX SDK ausentes/incompatíveis.
* Navegador não abre: EdgeDriver desatualizado (Ver no log). link para download: https://developer.microsoft.com/pt-br/microsoft-edge/tools/webdriver/?ch=1&form=MA13LH

# Observações Técnicas

* O projeto atualmente utiliza a abordagem `Shade + JPackage`
* O executável depende do JavaFX SDK instalado localmente
* O EdgeDriver deve ser compatível com a versão do Microsoft Edge instalada
* Mudanças estruturais no site automatizado podem exigir ajustes na automação
* O projeto ainda não utiliza modularização JPMS (`module-info.java`)


