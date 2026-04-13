# Automação de Formulários com Java + Selenium

Este projeto é uma **fase funcional de um sistema maior de automação web**, desenvolvido em Java, com foco na automação do preenchimento de formulários em sistemas web complexos (ex: JSF/AJAX).
- Objetivo

Automatizar o processo de preenchimento de formulários web a partir de dados estruturados, reduzindo trabalho manual, erros humanos e tempo operacional.

- Conceitos aplicados

* Programação orientada a objetos (POO)
* Padrão de projeto **Strategy** (suporte a múltiplos sites)
* Separação inicial de responsabilidades (estrutura preparada para evolução)
* Automação de interface web com Selenium
* Manipulação de dados externos (ex: planilhas)

- Tecnologias utilizadas

* Java 21
* Selenium WebDriver
* Microsoft Edge WebDriver
* JavaFX *(planejado para versões futuras)*

- Estrutura atual do projeto

A aplicação está organizada visando escalabilidade futura:

* Camada de automação responsável pela interação com o navegador
* Estratégias independentes para diferentes fluxos/sites
* Leitura e processamento de dados externos
* Lógica de preenchimento desacoplada (em evolução)

- Status do projeto

✅ Automação funcional de formulários
✅ Estrutura inicial baseada em padrões
🚧 Em desenvolvimento contínuo

- Próximos passos

* Implementação de Controllers (arquitetura MVC)
* Interface gráfica com JavaFX
* Melhor tratamento de exceções e logs
* Integração mais robusta com fontes de dados
* Refatoração para maior desacoplamento

- Como executar

1. Clone o repositório:
git clone https://github.com/seu-usuario/seu-repo.git

2. Configure o WebDriver (Edge):
* Certifique-se de que a versão do EdgeDriver é compatível com seu navegador https://developer.microsoft.com/pt-br/microsoft-edge/tools/webdriver/?ch=1&form=MA13LH

3. Execute a aplicação:
Via IDE (recomendado) ou via linha de comando.

- Observações

* O sistema pode depender de estruturas específicas do site automatizado (ex: IDs dinâmicos, AJAX)
* Pequenas mudanças no site podem exigir ajustes na automação
* Projeto ainda não possui interface gráfica
