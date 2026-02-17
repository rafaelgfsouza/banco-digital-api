🏦 API de Gerenciamento de Contas e Transferências Bancárias

API REST desenvolvida com Java 17 e Spring Boot 3 para gerenciamento de contas bancárias e realização de transferências com foco em segurança, boas práticas e arquitetura profissional.

🚀 Tecnologias e Conceitos Aplicados

Este projeto foi construído priorizando segurança, organização e padrões de mercado:

🔐 1. Proteção de Dados (DTO)

Implementação do padrão Data Transfer Object (DTO).

Impede a exposição de dados sensíveis como:

Senhas

CPF

E-mails

Apenas alguns dados são retornados nos endpoints para testes.

🔎 2. Lógica de Segurança

Validação obrigatória da senha da conta de origem.

Verificação de saldo disponível antes da transferência.

Bloqueio da operação em caso de inconsistências.

🗄️ 3. Persistência Profissional

Integração com PostgreSQL.

Utilização de Spring Data JPA para acesso a dados.

Modelagem baseada em boas práticas de ORM.

🐳 4. Infraestrutura com Docker

Projeto configurado com docker-compose.

Facilidade para subir o banco de dados.

Ambiente padronizado para desenvolvimento.

🔄 5. Transacionalidade (ACID)

Uso da anotação @Transactional.

Garantia de que a transferência seja:

✔️ Totalmente concluída

❌ Ou totalmente cancelada em caso de erro

🛠️ Como Executar o Projeto

1️⃣ Subir o Banco de Dados (Docker)
docker-compose up -d

2️⃣ Executar a Aplicação

Você pode iniciar a aplicação de duas formas:

▶️ Pelo IntelliJ: Executando a classe BancoDigitalApplication

▶️ Pelo terminal:

./mvnw spring-boot:run


A aplicação iniciará em:

http://localhost:8080

🔗 Endpoints e Exemplos de Uso

1️⃣ Criar uma Conta

Endpoint:

POST http://localhost:8080/clientes

{
"nome": "Rafael Dev",
"cpf": "123.456.789-00",
"email": "rafael@email.com",
"senha": "minhasenha123"
}


---


Login:

http://localhost:8080/auth/login

{
"numeroConta": "COLOQUE-AQUI-O-NUMERO-GERADO",
"senha": "minhasenha123"
}

---

Transferencia:
Com duas contas criadas com saldo:
http://localhost:8080/contas/transferir?numeroDestino=123-X&valor=100.00



Auth bearer token criado devolvido no login.

---

2️⃣ Listar Contas (Visão Segura)

Endpoint:

GET /contas


📌 Retorna apenas dados públicos via DTO.
A senha e demais dados sensíveis são omitidos.

📤 Exemplo de Resposta
[
{
"id": 4,
"numeroConta": "8877-X",
"saldo": 1500.00,
"nomeTitular": "Rafael Dev"
}
]

3️⃣ Realizar Transferência

Endpoint:

POST /contas/{idOrigem}/transferir/{idDestino}


📌 Parâmetros obrigatórios via Query Params:

valor

senha

📎 Exemplo de URL
http://localhost:8080/contas/4/transferir/5?valor=250.00&senha=minhasenha123

📌 Objetivo do Projeto

Projeto desenvolvido para portfólio técnico, com foco em:

Backend com Java

Segurança de Dados

Boas práticas de arquitetura

Transações bancárias seguras

Organização profissional de código