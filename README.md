# PicPay Wallet API 💸

API REST de carteira digital inspirada no PicPay, permitindo:

- cadastro de usuários
- consulta de dados do usuário
- transferência de saldo entre usuários
- listagem de transações de um usuário

Projeto criado para estudo e portfólio, focado em **Java + Spring Boot** e em boas práticas de API back-end.

---

## 📚 Tecnologias utilizadas

- **Java 17**
- **Spring Boot** (Web, Validation)
- **Spring Data JPA**
- **H2 Database** (banco em memória para desenvolvimento)
- **Lombok**
- **Maven**

---

## 🧱 Arquitetura da aplicação

Camadas principais:

- **controller** → expõe os endpoints REST (HTTP)
- **service** → regra de negócio (validações, transferências, etc.)
- **repository** → acesso ao banco de dados via Spring Data JPA
- **domain** → entidades do modelo de domínio (`User`, `Transaction`)
- **dto** → objetos de transporte de dados (request/response)
- **exception** → exceções de negócio + handler global de erros

Estrutura (simplificada):

```text
src/main/java/com/brunomarques/picpay_wallet_api
 ├── controller
 │    ├── UserController
 │    └── TransactionController
 ├── domain
 │    ├── User
 │    └── Transaction
 ├── dto
 │    ├── CreateUserRequest
 │    ├── UserResponse
 │    ├── CreateTransactionRequest
 │    └── TransactionResponse
 ├── exception
 │    ├── ErrorResponse
 │    ├── GlobalExceptionHandler
 │    ├── InsufficientBalanceException
 │    └── UserNotFoundException
 ├── repository
 │    ├── UserRepository
 │    └── TransactionRepository
 ├── service
 │    ├── UserService
 │    └── TransactionService
 └── PicpayWalletApiApplication
 
🚀 Como rodar o projeto localmente

Pré-requisitos

Java 17 instalado
Maven instalado (ou usar o Maven embutido da IDE)

Passos

# clonar o repositório
git clone https://github.com/BrunoApMarques/picpay-wallet-api.git
cd picpay-wallet-api

# build do projeto
mvn clean install

# subir a aplicação
mvn spring-boot:run

A API ficará disponível em:
http://localhost:8080

🔐 Modelagem principal

Entidade User
id (Long)

fullName (String)

document (String) – CPF ou similar

email (String)

balance (BigDecimal) – saldo da carteira

createdAt (LocalDateTime)

Regras:

documento e e-mail não podem ser duplicados

saldo inicial pode ser informado no cadastro ou começar em 0

Entidade Transaction
id (Long)

payer (User) – usuário que paga (de onde sai o saldo)

payee (User) – usuário que recebe

amount (BigDecimal)

createdAt (LocalDateTime)

Regras:

pagador e recebedor não podem ser o mesmo usuário

pagador precisa ter saldo suficiente

operação é feita dentro de uma transação (@Transactional)

🌐 Endpoints principais

1️⃣ Criar usuário

POST /users

Request body:

{
  "fullName": "Bruno Marques",
  "document": "12345678900",
  "email": "brunomarquesque@gmail.com",
  "initialBalance": 150.00
}

Possíveis respostas:
201 Created

{
  "id": 1,
  "fullName": "Bruno Marques",
  "document": "12345678900",
  "email": "brunomarquesque@gmail.com",
  "balance": 150.00,
  "createdAt": "2025-11-21T17:48:20.2191758"
}

400 Bad Request – validações de campos / e-mail ou documento já existentes
422 Unprocessable Entity – (se configurado para futuras validações específicas)

2️⃣ Buscar usuário por ID
GET /users/{id}

Exemplo:
GET /users/1

Resposta:
200 OK

{
  "id": 1,
  "fullName": "Bruno Marques",
  "document": "12345678900",
  "email": "brunomarquesque@gmail.com",
  "balance": 150.00,
  "createdAt": "2025-11-21T17:48:20.2191758"
}

404 Not Found – usuário não encontrado

3️⃣ Criar transação (transferência entre usuários)
POST /transactions

Request body:

{
  "payerId": 1,
  "payeeId": 2,
  "amount": 50.00
}

Fluxo:

valida se pagador e recebedor existem
valida se não são o mesmo usuário
valida se o pagador tem saldo suficiente
debita o saldo do pagador
credita o saldo do recebedor
registra a transação

Resposta:
201 Created

{
  "id": 1,
  "payerId": 1,
  "payeeId": 2,
  "amount": 50.00,
  "createdAt": "2025-11-21T23:11:22.4800874"
}

Erros possíveis:
400 Bad Request – request inválido (ex.: payerId = payeeId)
404 Not Found – usuário não existe
422 Unprocessable Entity – saldo insuficiente

4️⃣ Listar transações de um usuário
GET /users/{id}/transactions

Exemplo:
GET /users/1/transactions

Resposta:
200 OK

[
  {
    "id": 1,
    "payerId": 1,
    "payeeId": 2,
    "amount": 50.00,
    "createdAt": "2025-11-21T23:11:22.4800874"
  }
]

Se o usuário não tiver transações:
[]

Se o usuário não existir:
404 Not Found

⚠️ Tratamento global de erros

A API possui um GlobalExceptionHandler que transforma exceções de negócio em respostas JSON padronizadas, por exemplo:

{
  "timestamp": "2025-11-21T23:40:00Z",
  "status": 404,
  "error": "Usuário não encontrado",
  "message": "Usuário não encontrado com id: 999",
  "path": "/users/999"
}

Isso deixa a API mais consistente e fácil de consumir por outros serviços.

💡 Possíveis melhorias futuras

Autenticação e autorização (JWT / OAuth2)
Paginação de transações
Filtros de histórico por período
Integração com banco relacional (PostgreSQL / MySQL)
Testes unitários e de integração (JUnit + Mockito)
Dockerfile e docker-compose para subir ambiente completo

👤 Autor
Bruno Marques
Desenvolvedor Back-end


