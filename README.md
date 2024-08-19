[![GHA-CI](https://github.com/leonardomelgarejo/rest-assured-poc/actions/workflows/gha-ci.yml/badge.svg)](https://github.com/leonardomelgarejo/rest-assured-poc/actions/workflows/gha-ci.yml)

# Projeto de automação de testes de APIs Rest com as ferramentas Rest Assured + JUnit5 + Java

## Descrição

Este é um projeto de estudo sobre automação de testes de APIs Rest usando as ferramentas Rest Assured e JUnit5 com Java.

## Estrutura do Projeto

```plaintext
├── .github
│   └── workflows
│       └── gha-ci.yml
├── src
│   ├── main
│   │   ├── java
│   │   │    └── br
│   │   │        └── com
│   │   │            └── lmelgarejo
│   │   └── resources                   
│   └── test
│       ├── java
│       │   └── br
│       │       └── com
│       │            └── lmelgarejo
├── .gitignore
├── pom.xml
└── README.md
```

## Pré-requisitos

* [Java JDK 22+](https://www.oracle.com/pt/java/technologies/javase/jdk11-archive-downloads.html)

* [Apache Maven 3.6+](https://maven.apache.org/docs/3.6.0/release-notes.html)

* [JUnit Jupiter API 5.11.0+](https://testng.org/)

* [REST Assured 5.5.0+](https://www.selenium.dev/)

## Configuração do Ambiente

1 Clone o repositório:
```
git clone https://github.com/leonardomelgarejo/rest-assured-poc.git
```

2 Instale as dependências do Maven:
```
mvn clean install
```
## Estrutura dos Testes

* TO DO

## Executando os Testes
```
mvn clean test
```

## Relatório de Testes

* O relatório de testes é gerado pelo framework Allure Reports, de duas formas:
    * Localmente:
      * mvn allure:serve: Abrirá o relatório HTML no navegador
      * mvn allure:report: Irá gerar o HTML na pasta target/site/allure-maven-plugin
    * Remoto:
      * No link: https://leonardomelgarejo.github.io/rest-assured-poc/
