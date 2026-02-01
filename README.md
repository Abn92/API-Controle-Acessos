# 📦 Controle de Estoque - API RESTful

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

Aplicação completa para controle de inventário, suportando movimentações de entrada e saída, consultas gerenciais e integração entre back-end e front-end.

---

## 📝 Visão Geral
O sistema tem como objetivo gerenciar produtos e suas movimentações de estoque, permitindo o acompanhamento de saldo disponível, histórico de vendas e cálculo de lucro por produto, seguindo boas práticas de desenvolvimento.

---

## 🛠️ Tecnologias Utilizadas

### **Back-end**
* **Java 21**
* **Spring Boot**
* **Spring Data JPA**
* **H2 Database** (Banco em memória)
* **Maven**
* **JUnit 5 & Mockito** (Testes)

### **Front-end**
* **Framework:**  [Vue]
https://github.com/Abn92/UI-Controle-Acessos

---

## 🏗️ Estrutura do Projeto (Entidades)

### **Produto**
* `Código`, `Descrição`, `Tipo` (ELETRONICO, ELETRODOMESTICO, MOVEL).
* `Valor no fornecedor` e `Quantidade em estoque`.

### **Movimentação**
* `Tipo` (ENTRADA ou SAIDA).
* `Quantidade`, `Data` e `Valor de venda` (apenas para saídas).

---

## 🚀 Funcionalidades

### **Produtos**
- [x] CRUD completo (Cadastro, Listagem, Busca, Atualização e Remoção).

### **Estoque**
- [x] Entrada e Saída de produtos.
- [x] Validação de saldo (impede saída maior que o estoque disponível).

### **Consultas Gerenciais**
* **Por Tipo:** Exibe quantidade disponível e total de saídas.
* **Lucro:** Exibe total vendido e lucro total.
  * *Cálculo:* $$(Valor Venda - Valor Fornecedor) \times Quantidade Vendida$$

---

## 🗄️ Banco de Dados (H2)
Acesse o console para visualizar as tabelas em tempo real:
* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:testdb`
* **User:** `sa` | **Password:** `(vazio)`

---

## ⚙️ Como Executar

1. Clone o repositório.
2. Importe na sua IDE de preferência.
3. Execute a classe principal: `DesafioNexdomApplication`.
4. A API estará disponível em: `http://localhost:8080`.

---

## 💡 Boas Práticas
O projeto foi estruturado utilizando:
* **Separacão de responsabilidades:** Controller, Service, Repository, DTOs.
* **Clean Code.**
* **Testes Unitários** isolados de banco de dados.
