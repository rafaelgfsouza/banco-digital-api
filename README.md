DIGITAL BANK API — Secure Digital Account & Transfer Management
=================================================================

A RESTful API for managing digital bank accounts and performing secure financial transfers, built with Java 17 and Spring Boot 3.

This project demonstrates advanced backend engineering practices, secure system design, domain-driven structure, and strong transactional consistency in financial operations.


OVERVIEW
--------

The application provides:

- Customer registration with automatic account generation
- Stateless authentication using JWT (JSON Web Token)
- Secure account listing via DTO pattern
- Token-based identity validation for transfers
- Full ACID-compliant transactional guarantees
- Secure password hashing with BCrypt


ARCHITECTURE
------------

The system follows a layered architecture to ensure scalability, maintainability, and separation of concerns:

- Controller Layer
  Handles HTTP requests and response mapping.

- Service Layer
  Contains core business logic and critical validations.
  Responsible for transactional integrity.

- Repository Layer
  Data persistence abstraction using Spring Data JPA.

- DTO Layer
  Strict control of data exposure to prevent sensitive information leakage.

- Security Layer
  Authentication and authorization powered by Spring Security and JWT.


TECH STACK
----------

- Java 17
- Spring Boot 3
- Spring Data JPA
- Hibernate
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- BCrypt (Password hashing)
- Maven


SECURITY & DATA PROTECTION
--------------------------

DTO Pattern
Sensitive data such as passwords, CPF (Brazilian tax ID), and email addresses are never exposed in API responses.
Only strictly necessary public fields are returned.

Authentication & Identity Control
- Passwords are stored using BCrypt hashing.
- Successful login generates a signed JWT Bearer Token.
- The authenticated account is extracted from the JWT Subject.
- Users cannot manipulate or access accounts that do not belong to them.

Transactional Consistency (ACID)
All financial transfers are wrapped with @Transactional to guarantee:

- Atomicity: debit and credit occur together or not at all.
- Consistency: the system never persists invalid financial states.
- Isolation: concurrent operations are handled safely.
- Durability: committed transactions are permanently stored.

This prevents critical inconsistencies such as debiting without crediting.


API ENDPOINTS
-------------

1. Create Customer and Account
   POST /clientes

Creates a customer profile and automatically generates a bank account.

Request Body:
{
"nome": "Rafael Dev",
"cpf": "123.456.789-00",
"email": "rafael@email.com",
"senha": "securePassword"
}


2. Authentication (Login)
   POST /auth/login

Validates credentials and returns a JWT token.

Request Body:
{
"numeroConta": "3868-43",
"senha": "securePassword"
}

Response:
A String containing the signed JWT Bearer Token.


3. List Accounts (Secure View)
   GET /contas

Returns public account data only.
Requires Authorization header:

Authorization: Bearer <your_token_here>

Example Response:
[
{
"numeroConta": "3868-43",
"saldo": 1000.00,
"nomeTitular": "Rafael Dev"
}
]


4. Transfer Between Accounts
   POST /contas/transferir?numeroDestino=1122-33&valor=100.00

The origin account is securely identified through the JWT token sent in the request header.

Headers:
Authorization: Bearer <your_token_here>

Query Parameters:
- numeroDestino
- valor

Validation Rules:
- Authenticated identity verification
- Sufficient balance validation
- Prevention of unauthorized transfers
- Atomic transaction guarantee


HOW TO RUN
----------

1. Clone the repository:

git clone https://github.com/rafaelgfsouza/banco-digital-api.git

2. Configure your PostgreSQL credentials in:

src/main/resources/application.properties

3. Run the application:

./mvnw spring-boot:run

The application will start at:

http://localhost:8080


PROJECT PURPOSE
---------------

This project was developed to demonstrate:

- Professional backend engineering standards
- Secure financial transaction handling
- Clean layered architecture
- Strong authentication and identity control
- Production-oriented coding practices


Developed by Rafael Souza.
