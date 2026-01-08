# Banking API – Backend Challenge

This project implements a banking API following a **contract-first approach**,
using **OpenAPI 3.0** as the single source of truth.

The system is designed with a strong focus on **clean architecture**,
**domain-driven design**, and **reactive programming**.

---

## Design Principles

- Contract-first API design (OpenAPI)
- Clean Architecture (domain-centered)
- Domain-Driven Design (DDD)
- Reactive programming with Spring WebFlux
- Clear separation of concerns

---

## Domain Model

The core business logic is implemented as a **framework-agnostic domain layer**.

- **Customer**  
  Represents a bank client and acts as an aggregate root.

- **Account**  
  Represents a bank account owned by a customer.
  Balance is protected by domain rules and cannot be negative.

- **Movement**  
  Represents an immutable financial transaction
  (deposit or withdrawal) applied to an account.

---

## Business Rules

- Account balance is derived from movements.
- Movements are immutable and cannot be updated or deleted.
- Withdrawal movements require sufficient balance.
- Accounts use a soft-delete approach for closure.
- Domain logic is isolated from infrastructure and frameworks.

---

## Tech Stack

- Java 17
- Spring Boot 3 (WebFlux)
- Maven
- OpenAPI 3
- Docker

---

## API Contract

The API contract is defined in `openapi.yaml` and validated using
**OpenAPI Generator CLI**.

The Spring Boot project is generated directly from the OpenAPI contract,
ensuring alignment between API definition and implementation.

---

## Project Status

- [x] OpenAPI contract validated
- [x] Domain model implemented
- [ ] Application use cases
- [ ] Reactive WebFlux controllers
- [ ] Persistence layer
- [ ] Dockerization
