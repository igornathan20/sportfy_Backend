# sportfy_Backend
API em desenvolvimento para os sistema de gerenciamento de eventos esportivos da UFPR


## 1. Downloads necessários:
- Java: JDK V21.0.4+7 - https://adoptium.net/
- MySQL: V8.0.39 - https://dev.mysql.com/downloads/installer/

## 2. Noções básicas do projeto:
- \config: Contém classes de configuração do Spring, como configuração de beans, segurança, etc;
- \controllers: Contém os controladores REST que lidam com as requisições HTTP, no caso os end-points;
- \dtos: Contém os objetos de transferência de dados no intuito de omitir dados sensíveis como senhas presentes nas classes de padrão ORM;
- \enums: contém enums da regra de negócio do projeto;
- \exeptions: Contém classes para tratamento de exceções customizadas;
- \models: Contém as classes de modelo ORM (entidades) mapeadas no banco de dados;
- \repositories: Contém as interfaces que acessam a camada de dados;
- \security: Contém classes relacionadas à segurança, como configuração de autenticação e autorização;
- \service: Contém a lógica de negócios da aplicação;
- \util: Contém classes utilitárias que são usadas em todo o projeto, como manipular strings, datas, horas e fuso-horários e converter tipos diferentes entre classes;
- \SportfyApplication.java: inicia a execução da aplicação;
- \resources\db\migration: contém arquivos .sql que fazem a manipulação pré-definida no banco de dados assim que o projeto é iniciado, por meio da ferramenta flyway;
- \resources\application.properties: contém as configurações para se conectar a diversos serviços como no banco de dados e envio de emails;
- pom.xml: contém todas as dependências do projeto;
