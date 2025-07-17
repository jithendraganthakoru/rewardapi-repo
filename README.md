                          *********** Customer Transaction Rewards Points API *************

This Spring Boot application calculates monthly and total reward points for customers based on their transaction history. 

It exposes a REST API endpoint that accepts customerId as a pathvariable and returns reward point details per customer.

UseCase:

a)Exposes an endpoint and accepts customerId as input pathvariable

b)Calculates reward points:
   a)2 points for every dollar spent over $100
   b)1 point for every dollar spent between $50 and $100
   c)0 points for every dollar spent less than $50

c)The restful api will Returns structured response with customer ID, monthly points, and total points.

Implementation:

As part of the springboot application we have created the below layers 

  a)Controller layer (RestController) -to handle http requests

  b)Service Layer - Service class will internally use the repoistory object to get the data based on customer id.
  
  c)Repository Layer - Custom dataset created. which returns the customer transactions data based on customer id.

  c) Global Exception Handler - To handle exception globally if unknown exceptions


Junit Test Cases:

Integration Test cases - to test the whole flow of the application

Junit Test Cases using junit5 and Mockito framework annotations

Technology Used:
a)Java17
b)springboot 3.5.3
c)Junit5
d)Mockito
e)Lombok

Url: http://localhost:2025/getCustomerRewardPointDetails/{customerId}   HttpMethod: GET

pathvariable (customerId) ranges from "1001" to "1005"

customerId: "1001"
==================
Response:
========

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

customerId: "1005"
=================

Response:
========

[
  {
    "customerId": "1005",
    "monthlyPoints": {
      "June": 150,
      "May": 210,
      "July": 166
    },
    "totalPoints": 526
  }
]