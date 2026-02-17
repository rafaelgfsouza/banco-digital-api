🏦 Banking API — Digital Account & Secure Transfer Management

A RESTful API for managing digital bank accounts and performing secure financial transfers, built with Java 17 and Spring Boot 3.

This project demonstrates backend engineering best practices, secure architecture design, domain modeling, and transactional consistency in financial operations.

📌 Overview

The application provides:

Bank account creation

JWT-based authentication

Secure account listing (DTO-based exposure)

Account-to-account transfers with full validation

ACID-compliant transactional guarantees

Dockerized infrastructure for reproducible environments

🧱 Architecture

The application follows a layered architecture:

Controller → Service → Repository → Database

Responsibility Separation

Controller → HTTP layer and request handling

Service → Business rules and validation logic

Repository → Data persistence (Spring Data JPA)

DTOs → Data exposure control

Entities → Domain model representation

Security Layer → Authentication & authorization (JWT)

This structure ensures maintainability, testability, and scalability.

🚀 Tech Stack

Java 17

Spring Boot 3

Spring Data JPA

Spring Security

JWT (Bearer Token Authentication)

PostgreSQL

Docker & Docker Compose

Maven

🔐 Security & Data Protection
✔️ DTO Pattern

Sensitive information is never exposed through API responses.

The following data is protected:

Passwords

CPF (Brazilian tax ID)

Email

Only safe, public-facing fields are returned via DTOs.

✔️ Authentication & Authorization

Authentication via /auth/login

JWT token generation

Bearer Token required for protected endpoints

Password validation required before critical operations

✔️ Business Rule Enforcement

During transfers, the system validates:

Correct origin account password

Sufficient available balance

Valid destination account

Prevention of self-transfer

Operation integrity before commit

Any validation failure automatically aborts the operation.

🔄 Transactional Consistency (ACID)

The @Transactional annotation ensures full ACID compliance:

Atomicity → The transfer fully succeeds or fully rolls back

Consistency → The database never reaches an invalid state

Isolation → Concurrent operations are handled safely

Durability → Committed transactions are permanently stored

This prevents critical financial inconsistencies such as debiting without crediting.

🗄️ Persistence Layer

PostgreSQL as relational database

ORM via Spring Data JPA

Proper entity mapping

Clear separation between Entities and DTOs

Clean repository abstraction

🐳 Infrastructure

The project includes Docker configuration for environment standardization.

Start database:
docker-compose up -d


Benefits:

Reproducible environment

Fast setup

Isolation from local configuration issues

Consistent development workflow

🛠️ Running the Application
1️⃣ Start the Database
docker-compose up -d

2️⃣ Run the Application

Via IntelliJ:

Run BancoDigitalApplication


Or via terminal:

./mvnw spring-boot:run


Application runs at:

http://localhost:8080

🔗 API Endpoints
🔹 Create Account
POST /clientes

{
"nome": "Rafael Dev",
"cpf": "123.456.789-00",
"email": "rafael@email.com",
"senha": "securePassword123"
}

🔹 Login
POST /auth/login

{
"numeroConta": "XXXX-X",
"senha": "securePassword123"
}


Returns:

JWT Bearer Token

🔹 List Accounts (Secure View)
GET /contas


Example response:

[
{
"id": 4,
"numeroConta": "8877-X",
"saldo": 1500.00,
"nomeTitular": "Rafael Dev"
}
]

🔹 Transfer Between Accounts
POST /contas/{idOrigem}/transferir/{idDestino}?valor=250.00&senha=securePassword123


Validation rules applied:

Correct password

Sufficient balance

Valid accounts

Atomic transaction guarantee

🧠 Technical Decisions

DTO usage to prevent sensitive data leakage

Centralized business rules in Service layer

Security layer decoupled from domain logic

Transaction management at service level

Designed for scalability and maintainability

📈 Future Improvements

Unit tests with JUnit & Mockito

Integration tests with Testcontainers

API documentation with Swagger / OpenAPI

Optimistic locking for concurrency control

Cloud deployment (AWS / Railway / Render)

CI/CD pipeline with GitHub Actions

🎯 Project Purpose

This project was developed to demonstrate:

Strong backend engineering skills

Secure application design

Clean architecture principles

Financial transaction integrity

Production-ready coding standards