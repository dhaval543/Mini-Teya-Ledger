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


## Example 
**Request**
POST /api/v1/ledger/111/deposit
```json
{
  "amount": 100
}
**Response**
{
  "id": 1,
  "type": "deposit",
  "amount": 100,
  "timestamp": "2025-10-07T10:31:42.6129622+05:30",
  "balanceAfter": 100
}

POST /api/v1/ledger/111/deposit

{
  "amount": "one hundred"
}

Error: response status is 400

Invalid request format: Cannot deserialize value of type `java.lang.Double` from String "one hundred": not a valid `Double` value
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 2, column: 13] (through reference chain: com.teya.ledger.request.TransactionRequest["amount"])

POST /api/v1/ledger/111/withdraw

{
  "amount": 50
}

{
  "id": 3,
  "type": "WITHDRAWAL",
  "amount": 50,
  "timestamp": "2025-10-07T10:39:39.941282+05:30",
  "balanceAfter": 5050
}
POST /api/v1/ledger/111/withdraw
{
  "amount": 601
}
{
  "error": "Daily withdrawal limit of 600.0 exceeded"
}

GET /api/v1/ledger/111/balance
5050

GET /api/v1/ledger/111/TransactionHistory


[
  {
    "id": 1,
    "type": "deposit",
    "amount": 100,
    "timestamp": "2025-10-07T10:31:42.6129622+05:30",
    "balanceAfter": 100
  },
  {
    "id": 2,
    "type": "deposit",
    "amount": 5000,
    "timestamp": "2025-10-07T10:37:51.8007256+05:30",
    "balanceAfter": 5100
  },
  {
    "id": 3,
    "type": "WITHDRAWAL",
    "amount": 50,
    "timestamp": "2025-10-07T10:39:39.941282+05:30",
    "balanceAfter": 5050
  }
]


