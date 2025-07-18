# Customer Transaction Rewards Points API

This Spring Boot application calculates monthly and total reward points for customers based on their transaction history.

---

## 📌 Use Case

- Exposes a REST API endpoint that accepts `customerId` as a path variable.
- Calculates reward points:
  - 🟢 2 points for every dollar spent over $100
  - 🟡 1 point for every dollar spent between $50 and $100
  - 🔴 0 points for every dollar spent less than $50
- Returns structured response with customer ID, monthly points, and total points.

---

## ⚙️ Implementation

### Project Structure

