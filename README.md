# Banking API – Backend Challenge

This project implements a banking API following a **contract-first approach**
using OpenAPI 3.0 as the single source of truth.

## Design Principles
- Contract First (OpenAPI)
- Clean Architecture
- Reactive programming with Spring WebFlux
- Domain-driven design
- Separation of concerns

##  Domain Model
- **Customer**: Represents a bank client.
- **Account**: Represents a bank account owned by a customer.
- **Movement**: Immutable financial transaction (debit / credit).

##  Business Rules
- Account balance is calculated through movements.
- Movements cannot be updated or deleted.
- Debit movements require sufficient balance.
- Accounts are closed using a soft-delete approach.

## Tech Stack
- Java 17
- Spring Boot 3 (WebFlux)
- Maven
- OpenAPI 3
- Docker

## API Contract
The API contract is defined in `openapi.yaml` and validated using
OpenAPI Generator CLI.

## Project Status
- [x] OpenAPI contract validated
- [ ] Spring Boot WebFlux implementation
- [ ] Domain logic
- [ ] Dockerization
