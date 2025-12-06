# 📚Sistema de Gerenciamento de Biblioteca

Este repositório contém um **Sistema de Gerenciamento de Biblioteca** composto por:

-   **Back-end:** Spring Boot (API REST)
-   **Front-end:** JavaFX (interface desktop)
-   **Banco de dados:** JSON
-   **Build Tools:** Maven

------------------------------------------------------------------------

## 📌 Funcionalidades

### **Back-end (API - Spring Boot)**

-   Cadastro, listagem, atualização e exclusão de entidades como:
    -   Livro
    -   Usuário
-   Padrões aplicados:
    -   MVC
    -   DTOs
    -   Services
    -   Repositories


### 🟦 **Livros (`/livros`)**

| Método | Endpoint         | Descrição                                  | Corpo / Retorno |
|--------|-------------------|----------------------------------------------|------------------|
| POST   | `/livros/`        | Criar um novo livro                          | `Livro` no body |
| GET    | `/livros/`        | Listar todos os livros                       | Lista de `Livro` |
| GET    | `/livros/{id}`    | Buscar livro por ID                          | `Livro` |
| PUT    | `/livros/{id}`    | Atualizar um livro usando `UpdateLivroDTO`   | `Livro` atualizado |
| DELETE | `/livros/{id}`    | Deletar livro por ID                         | `204 No Content` |



### 🟩 **Usuários (`/usuarios`)**

| Método | Endpoint           | Descrição                                      | Corpo / Retorno |
|--------|---------------------|------------------------------------------------|------------------|
| POST   | `/usuarios/`        | Criar um novo usuário                          | `Usuario` no body |
| GET    | `/usuarios/`        | Listar todos os usuários                       | Lista de `Usuario` |
| GET    | `/usuarios/{id}`    | Buscar usuário por ID                          | `Usuario` |
| PUT    | `/usuarios/{id}`    | Atualizar dados com `UpdateUsuarioDTO`         | `Usuario` atualizado |
| DELETE | `/usuarios/{id}`    | Deletar usuário por ID                         | `204 No Content` |



### 🟧 **Empréstimos (`/geral`)**

| Método | Endpoint                                      | Descrição                              | Retorno |
|--------|------------------------------------------------|------------------------------------------|---------|
| PUT    | `/geral/emprestar/{idLivro}/{idUsuario}`       | Emprestar um livro                       | `Livro` ou `409` com mensagem |
| PUT    | `/geral/devolver/{idLivro}/{idUsuario}`        | Devolver um livro                        | `Livro` ou `409` com mensagem |



✔ Todos os endpoints retornam `404` caso o recurso não seja encontrado.  
✔ Operações de empréstimo e devolução retornam **mensagem de erro (409 Conflict)** se a regra de negócio impedir a operação.


------------------------------------------------------------------------

### **Front-end (JavaFX)**

-   Interface construída FXML
-   Telas para:
    -   Gerenciamento de alunos e professores
    -   Visualização de dados
    -   Formulários e navegação GUI
-   Organização modular:
    -   Controllers
    -   Views
    -   Services de comunicação com API

------------------------------------------------------------------------

## 🚀 Como Executar o Projeto

### **1. Pré‑requisitos**

-   Java 17+
-   Maven 3.9+

------------------------------------------------------------------------

## 🔧 Executando o Sistema

### **Back-end (API)**

#### **Via Maven**
1. Abra o projeto no IntelliJ/STS/Eclipse.
2. Vá até o pacote onde está localizada a classe:
``` bash
src/main/java/com.lp2.bibliotecaapi/BibliotecaApiApplication.java
```
3. Clique com o botão direito na classe e selecione "Run 'BibliotecaApiApplication'" 

------------------------------------------------------------------------

### **Front-end (JavaFX)**

1. Abra o projeto no IntelliJ/STS/Eclipse.
2. Vá até o pacote onde está localizada a classe:
``` bash
src/main/java/com.lp2.bibliotecaapi/FXLauncher.java
```
3. Clique com o botão direito na classe e selecione "Run 'FXLauncher'" 

------------------------------------------------------------------------

## 🗂️ Estrutura do Repositório

    /
    ├── api/
    │   ├── src/main/java/... (código da API)
    │   ├── src/main/resources/
          ├── data/ (dados JSON)
          ├── fxml/ (codigo em FXML)
    │   ├── pom.xml
    │
    ├── README.md

------------------------------------------------------------------------

## 🛠️ Tecnologias Utilizadas

### **Back-end**

-   Spring Boot
-   Spring Web
-   Spring Data JPA
-   Maven

### **Front-end**

-   JavaFX
-   FXML

