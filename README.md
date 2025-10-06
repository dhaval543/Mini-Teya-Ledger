# Tiny Ledger API

This is a small Spring Boot project that works like a basic money ledger.  
You can deposit, withdraw, check balance, and view transaction history using simple REST APIs.  
Everything is stored in memory (no database).

---

## Features

- Deposit and withdraw money  
- View current balance  
- View all transactions  
- Daily withdrawal limit: **600 per day per user**  
- Simple admin reset endpoint (to clear all data)  
- Input validation (no negative or zero amounts)  
- Swagger/OpenAPI documentation  
- Thread-safe (synchronized blocks)  

---

## Tech Stack

- Java 17  
- Spring Boot 3.5.6  
- Maven  
- JUnit 5 + Mockito  
- Swagger (springdoc-openapi)  

---

## How to Run

1. Make sure you have Java 17+ and Maven installed.
2. Clone the repo:
3. cd tiny-ledger
4. mvn clean install
   mvn spring-boot:run
5. http://localhost:8282/swagger-ui/index.html

| Method | Endpoint                                    | Description       |
| ------ | ------------------------------------------- | ----------------- |
| POST   | `/api/v1/ledger/{accId}/deposit`            | Deposit money     |
| POST   | `/api/v1/ledger/{accId}/withdraw`           | Withdraw money    |
| GET    | `/api/v1/ledger/{accId}/balance`            | Check balance     |
| GET    | `/api/v1/ledger/{accId}/TransactionHistory` | View transactions |
| POST   | `/api/v1/ledger/admin/reset`                | Reset all data    |




