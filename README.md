# Customer Transaction Rewards Points API

This Spring Boot application calculates monthly and total reward points for customers based on their transaction history. It exposes RESTful endpoints to retrieve reward point details for individual customers or all customers.

---

##  Use Case

The system processes customer transaction data and calculates reward points based on the following rules:

-  **2 points** for every dollar spent **over $100**
-  **1 point** for every dollar spent **between $50 and $100**
-  **0 points** for every dollar spent **less than $50**

The API returns a structured response with:
- Customer ID
- Monthly reward points
- Total reward points

---

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3.5.3
- Spring Web
- JUnit 5
- Mockito
- Lombok
- Maven

---

##  Project Structure
rewardapi-repo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/customer/rewards/
│   │   │       ├── RewardPointApplication.java
│   │   │       ├── controller/
│   │   │       │   └── RewardPointController.java
│   │   │       ├── exception/
│   │   │       │   ├── CustomerNotFoundException.java
│   │   │       │   └── RewardPointExceptionHandler.java
│   │   │       ├── model/
│   │   │       │   ├── RewardResponse.java
│   │   │       │   └── Transaction.java
│   │   │       ├── repository/
│   │   │       │   └── RewardsPointRepository.java
│   │   │       └── service/
│   │   │           ├── RewardPointService.java
│   │   │           └── RewardPointServiceImpl.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/customer/rewards/
│               ├── RewardPointApplicationTests.java
│               ├── controller/
│               │   └── RewardPointControllerTest.java
│               └── service/
│                   └── RewardPointServiceImplTest.java


## Implementation:


As part of the springboot application we have created the below layers 
## 🧩 Application Layers

- **Controller Layer**: Handles HTTP requests.
- **Service Layer**: Contains business logic and interacts with the repository.
- **Repository Layer**: Provides mock transaction data for customers.
- **Exception Handling**: Global exception handler for consistent error responses.
- **Testing**:
  - Unit tests using JUnit 5 and Mockito.
  - Integration tests for end-to-end validation.

## API Endpoints:

### 1. Get Reward Points for a Specific Customer

- **URL**: `http://localhost:2025/getCustomerRewardPointDetail/{customerId}`

- **Method**: `GET`

- **Example**: `http://localhost:2025/getCustomerRewardPointDetail/1001`
  
#### Sample Response:
```json
[
  {
    "customerId": "1001",
    "monthlyPoints": {
      "June": 70,
      "May": 90
    },
    "totalPoints": 160
  }
]

---

### 2. Get Reward Points for All Customers

- **URL**: `http://localhost:2025/getAllCustomerRewardPointDetails`

- **Method**: `GET`

#### Sample Response:

```json

[
  {
    "customerId": "1005",
    "monthlyPoints": {
      "June": 150,
      "May": 210,
      "July": 166
    },
    "totalPoints": 526
  },
  {
    "customerId": "1004",
    "monthlyPoints": {
      "June": 190,
      "July": 170
    },
    "totalPoints": 360
  },
  {
    "customerId": "1003",
    "monthlyPoints": {
      "May": 210
    },
    "totalPoints": 210
  },
  {
    "customerId": "1002",
    "monthlyPoints": {
      "June": 50,
      "May": 150
    },
    "totalPoints": 200
  },
  {
    "customerId": "1001",
    "monthlyPoints": {
      "June": 70,
      "May": 90
    },
    "totalPoints": 160
  }
]
