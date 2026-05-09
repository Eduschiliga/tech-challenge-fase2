## 1. Visão Geral do Projeto e Arquitetura

Este projeto é uma aplicação Java/Spring Boot, que segue uma estrutura MVC (Model-View-Controller), mas adere aos
princípios de **Clean Architecture** (ou Arquitetura Hexagonal) e **Domain-Driven Design (DDD)**.

**Princípios Arquiteturais Chave:**

* **Clean Architecture / Arquitetura Hexagonal:** O domínio (regras de negócio) deve ser o centro, independente de
  frameworks, bancos de dados ou interfaces de usuário. As dependências devem fluir de fora para dentro (
  Infrastructure -> Application -> Domain).
* **Domain-Driven Design (DDD):** Foco na criação de um modelo de domínio rico, que encapsula comportamento e dados,
  refletindo a complexidade do negócio. Evitar **Modelos Anêmicos** (objetos com apenas getters/setters e sem lógica de
  negócio).
* **SOLID Principles:** Todos os componentes devem aderir aos princípios SOLID:
    * **S**ingle Responsibility Principle (SRP): Cada classe/módulo deve ter uma única razão para mudar.
    * **O**pen/Closed Principle (OCP): Entidades de software devem ser abertas para extensão, mas fechadas para
      modificação.
    * **L**iskov Substitution Principle (LSP): Objetos em um programa devem ser substituíveis por instâncias de seus
      subtipos sem alterar a correção do programa.
    * **I**nterface Segregation Principle (ISP): Clientes não devem ser forçados a depender de interfaces que não
      utilizam.
    * **D**ependency Inversion Principle (DIP): Módulos de alto nível não devem depender de módulos de baixo nível.
      Ambos devem depender de abstrações.

## 2. Stack Tecnológica

* **Linguagem:** Java versão LTS 21
* **Framework:** Spring Boot 3.4.1
* **Build Tool:** Maven
* **Cache:** Redis
* **Mensageria:** Kafka
* **Banco de Dados:** Abstraído via JPA/Hibernate, utilizado o banco PostgreSQL
* **Testes:** JUnit 5, Mockito, AssertJ
* **Mapeamento:** MapStruct (para DTOs/Entidades, quando aplicável)

## 3. Estrutura de Código e Convenções

O Copilot deve seguir a seguinte estrutura de pacotes e convenções de código:

* **`model`:** Contém as entidades de domínio, objetos de valor e agregados.
* **`model\entity`:** Contém as entidades de banco de dados.
* **`model\dto`:** Contém os objetos de transferência de dados.
* **`model\request`:** Contém os objetos de solicitação para APIs REST.
* **`model\response`:** Contém os objetos de resposta para APIs REST.
* **`enums`:** Contém as enumerações utilizadas preferencialmente separadas por módulo da aplicação.
* **`exception`:** Contém as classes de exceção customizadas separadas por módulo da aplicação.
* **`config`:** Contém as classes de configuração da aplicação separadas por módulo por exemplo: filter, security, interceptors.
* **`util`:** Contém classes utilitárias reutilizáveis.
* **`repository`:** Interfaces de repositório para acesso a dados, utilizando Spring Data JPA.
* **`service`:** Contém a lógica de negócio e coordenação entre repositórios e outros serviços, separado por módulo.
* **`controller`:** Exposição de APIs REST, utilizando Spring MVC, sempre documentadas com as anotações do Swagger como Operation com summary e description.
* **`mapper`:** Contém as interfaces de mapeamento do MapStruct para conversão entre entidades, DTOs, requests e responses.
* **`rule`:** Contém as regras de negócio e validações utilizado o padrão de projeto Strategy.

**Convenções de Código:**

* **`Nomenclatura`:** Padrão Java (camelCase para variáveis/métodos, PascalCase para classes/interfaces). Nomes devem ser descritivos e em português.
* **`Qualidade`:** Código limpo, legível, com alta coesão e baixo acoplamento. Evitar duplicação de código.
* **`Documentação`:** Métodos públicos e classes complexas devem ser documentados com Javadoc, evitar comentários desnecessários e redundantes.
* **`Imutabilidade`:** Preferir objetos imutáveis(records) sempre que possível, especialmente para objetos de valor e DTOs.

## 4. Estratégia de Testes Automatizados

Testes são uma parte integral do desenvolvimento. O Copilot deve sempre sugerir e auxiliar na criação de testes para o código gerado ou modificado.
Apenas testes unitários são necessários, mas eles devem ser abrangentes e seguir as melhores práticas de teste.

* **Testes Unitários:**
    * Foco em testar uma única unidade de código (classe, método) isoladamente.
    * Utilizar **Mockito** para mockar dependências externas (repositórios, serviços, etc.).
    * Garantir alta cobertura de código para as classes de domínio e aplicação.
    * Utilizar **JUnit 5** para a estrutura de testes e **AssertJ** para asserções fluentes.

## 5. Instruções Específicas para o Copilot

Ao gerar ou refatorar código, o Copilot deve:

* **Pergunta:** SEMPRE perguntar ao usuário sobre o contexto e os requisitos específicos antes de gerar código, para garantir que o código gerado atenda às necessidades do projeto.
* **Modelos Ricos:** Ao criar entidades de domínio, garantir que elas contenham lógica de negócio relevante, em vez de serem apenas estruturas de dados.
* **Testabilidade:** O código gerado deve ser facilmente testável.
* **Tratamento de Erros:** Incluir tratamento de exceções robusto e semântico, utilizando exceções customizadas quando apropriado.
* **Segurança:** Ao lidar com autenticação, autorização ou dados sensíveis, seguir as melhores práticas de segurança e utilizar os mecanismos de segurança do Spring Security.
* **Performance:** Considerar implicações de performance, especialmente em operações de banco de dados e processamento de grandes volumes de dados, em implementação de findAll priorizar por querys nativas com retornos simplificados a fim de ganho de performance.

## 6. Comandos de Build e Execução (Exemplos)

O Copilot deve estar ciente dos comandos comuns para gerenciar o projeto:

* **Maven:**
    * `mvn clean install`: Limpa, compila, testa e empacota o projeto.
    * `mvn test`: Executa todos os testes.
    * `mvn spring-boot:run`: Inicia a aplicação Spring Boot.