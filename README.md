# 💰 Expense Tracker API

A robust, production-grade RESTful API for personal expense tracking and budget management built with Spring Boot and MongoDB.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen?style=flat-square&logo=springboot)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-green?style=flat-square&logo=mongodb)
![JWT](https://img.shields.io/badge/JWT-Auth-blue?style=flat-square&logo=jsonwebtokens)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Testing with Postman](#-testing-with-postman)
- [Error Handling](#-error-handling)
- [Security](#-security)
- [Contributing](#-contributing)

---

## ✨ Features

- **🔐 Secure Authentication** - JWT-based authentication with access and refresh tokens
- **💸 Expense Management** - Full CRUD operations for tracking expenses and income
- **📁 Category Organization** - Custom categories with colors and icons
- **📊 Analytics Dashboard** - Comprehensive spending insights and summaries
- **👤 User Profiles** - Personalized user accounts with budget settings
- **✅ Input Validation** - Robust request validation with detailed error messages
- **🛡️ Security** - Spring Security with role-based access control

---

## 🛠 Tech Stack

| Technology | Description |
|------------|-------------|
| **Java 17** | Programming Language |
| **Spring Boot 3.2.2** | Application Framework |
| **Spring Security** | Authentication & Authorization |
| **Spring Data MongoDB** | Database Integration |
| **MongoDB Atlas** | Cloud Database |
| **JWT (jjwt 0.12.3)** | Token-based Authentication |
| **Lombok** | Boilerplate Code Reduction |
| **Maven** | Build Tool & Dependency Management |

---

## 🏗 Architecture

```
src/main/java/com/expensetracker/
├── config/                 # Security & Application Configuration
├── controller/             # REST API Controllers
│   ├── analytics/          # Analytics endpoints
│   ├── auth/               # Authentication endpoints
│   ├── category/           # Category endpoints
│   ├── expense/            # Expense endpoints
│   └── user/               # User profile endpoints
├── dto/
│   ├── request/            # Request DTOs
│   └── response/           # Response DTOs
├── exception/              # Custom Exceptions & Global Handler
├── model/                  # MongoDB Document Models
├── repository/             # MongoDB Repositories
├── service/                # Business Logic Layer
└── util/                   # Utility Classes (JWT, etc.)
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB Atlas account (or local MongoDB instance)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Sachith77/SpringBoot_ExpenseTracker.git
   cd SpringBoot_ExpenseTracker
   ```

2. **Configure environment variables**
   
   Create `application-dev.yml` or set environment variables:
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb+srv://<username>:<password>@<cluster>.mongodb.net/expense_tracker
   
   jwt:
     secret: your-256-bit-secret-key
     expiration: 86400000
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   ```
   http://localhost:8080/api
   ```

---

## 📚 API Endpoints

### Base URL
```
http://localhost:8080
```

### Quick Reference

| # | Method | Endpoint | Description | Auth |
|---|--------|----------|-------------|------|
| 1 | POST | `/api/auth/register` | Register new user | ❌ |
| 2 | POST | `/api/auth/login` | Login user | ❌ |
| 3 | GET | `/api/users/me` | Get current user profile | ✅ |
| 4 | POST | `/api/categories` | Create category | ✅ |
| 5 | GET | `/api/categories` | Get all categories | ✅ |
| 6 | POST | `/api/expenses` | Create expense | ✅ |
| 7 | GET | `/api/expenses` | Get all expenses | ✅ |
| 8 | PUT | `/api/expenses/{id}` | Update expense | ✅ |
| 9 | DELETE | `/api/expenses/{id}` | Delete expense | ✅ |
| 10 | GET | `/api/analytics/summary` | Get analytics summary | ✅ |

---

## 🧪 Testing with Postman

### Step 1: Register a New User

```http
POST http://localhost:8080/api/auth/register
Content-Type: application/json
```

```json
{
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "SecurePass123",
  "firstName": "John",
  "lastName": "Doe"
}
```

> ⚠️ **Copy the `accessToken` from the response for all subsequent requests!**

---

### Step 2: Login User

```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json
```

```json
{
  "usernameOrEmail": "johndoe",
  "password": "SecurePass123"
}
```

---

### Step 3: Get Current User Profile

```http
GET http://localhost:8080/api/users/me
Authorization: Bearer <your_access_token>
```

---

### Step 4: Create Food Category

```http
POST http://localhost:8080/api/categories
Authorization: Bearer <your_access_token>
Content-Type: application/json
```

```json
{
  "name": "Food category",
  "description": "Food and restaurant expenses",
  "color": "#FF5733",
  "icon": "restaurant"
}
```

> 📝 **Save the category `id` from response - needed for expenses!**

---

### Step 5: Create Transportation Category

```http
POST http://localhost:8080/api/categories
Authorization: Bearer <your_access_token>
Content-Type: application/json
```

```json
{
  "name": "Transportation",
  "description": "Travel and commute expenses",
  "color": "#3498DB",
  "icon": "directions_car"
}
```

> 📝 **Save this category `id` too!**

---

### Step 6: Get All Categories

```http
GET http://localhost:8080/api/categories
Authorization: Bearer <your_access_token>
```

---

### Step 7: Create Expense (Grocery)

```http
POST http://localhost:8080/api/expenses
Authorization: Bearer <your_access_token>
Content-Type: application/json
```

```json
{
  "title": "Grocery Shopping",
  "description": "Weekly groceries",
  "amount": 150.50,
  "expenseType": "EXPENSE",
  "expenseDate": "2026-02-01",
  "categoryId": "<FOOD_CATEGORY_ID>",
  "notes": "Vegetables and fruits",
  "recurring": false
}
```

> ⚠️ **Replace `<FOOD_CATEGORY_ID>` with actual ID from Step 4!**

---

### Step 8: Create Income (Salary)

```http
POST http://localhost:8080/api/expenses
Authorization: Bearer <your_access_token>
Content-Type: application/json
```

```json
{
  "title": "Monthly Salary",
  "description": "January salary",
  "amount": 5000,
  "expenseType": "INCOME",
  "expenseDate": "2026-01-31",
  "categoryId": null,
  "notes": "Full-time job",
  "recurring": true,
  "recurringFrequency": "MONTHLY"
}
```

---

### Step 9: Create Expense (Transportation)

```http
POST http://localhost:8080/api/expenses
Authorization: Bearer <your_access_token>
Content-Type: application/json
```

```json
{
  "title": "Uber Ride",
  "description": "Office commute",
  "amount": 25,
  "expenseType": "EXPENSE",
  "expenseDate": "2026-02-01",
  "categoryId": "<TRANSPORT_CATEGORY_ID>",
  "notes": "Morning ride",
  "recurring": false
}
```

> ⚠️ **Replace `<TRANSPORT_CATEGORY_ID>` with actual ID from Step 5!**

---

### Step 10: Get All Expenses

```http
GET http://localhost:8080/api/expenses
Authorization: Bearer <your_access_token>
```

---

### Step 11: Update Expense

```http
PUT http://localhost:8080/api/expenses/{expenseId}
Authorization: Bearer <your_access_token>
Content-Type: application/json
```

```json
{
  "title": "Grocery Shopping - Updated",
  "description": "Local market",
  "amount": 175,
  "expenseType": "EXPENSE",
  "expenseDate": "2026-02-01",
  "categoryId": "<FOOD_CATEGORY_ID>",
  "notes": "Organic items",
  "recurring": false
}
```

> ⚠️ **Replace `{expenseId}` with actual expense ID!**

---

### Step 12: Delete Expense

```http
DELETE http://localhost:8080/api/expenses/{expenseId}
Authorization: Bearer <your_access_token>
```

> ⚠️ **Replace `{expenseId}` with actual expense ID!**

---

### Step 13: Get Analytics Summary

```http
GET http://localhost:8080/api/analytics/summary
Authorization: Bearer <your_access_token>
```

**Sample Response:**
```json
{
  "success": true,
  "data": {
    "totalExpenses": 175.50,
    "totalIncome": 5000.00,
    "balance": 4824.50,
    "expenseCount": 3,
    "categoryCount": 2,
    "expensesByCategory": {
      "Food category": 150.50,
      "Transportation": 25.00
    }
  }
}
```

---

## 🔧 Postman Setup Tips

### Setting Authorization Header

**Option 1: Manual Header**
- Add header: `Authorization: Bearer <your_token>`

**Option 2: Postman Authorization Tab**
1. Go to **Authorization** tab
2. Select **Type:** `Bearer Token`
3. Paste your `accessToken`

### Using Environment Variables (Recommended)

1. Create a Postman Environment
2. Add variables:
   - `baseUrl` = `http://localhost:8080`
   - `token` = (update after login)
3. Use `{{baseUrl}}/api/expenses` in requests
4. Use `{{token}}` in Authorization

---

## ⚠️ Error Handling

### Standard Error Response

```json
{
  "success": false,
  "message": "Error description",
  "timestamp": "2026-02-01T10:30:00"
}
```

### HTTP Status Codes

| Code | Description |
|------|-------------|
| `200` | OK - Request successful |
| `201` | Created - Resource created |
| `400` | Bad Request - Invalid data |
| `401` | Unauthorized - Invalid/missing token |
| `403` | Forbidden - Access denied |
| `404` | Not Found - Resource not found |
| `409` | Conflict - Duplicate resource |
| `500` | Server Error |

### Common Errors

**401 Unauthorized:**
```json
{
  "success": false,
  "message": "Full authentication is required"
}
```

**404 Not Found:**
```json
{
  "success": false,
  "message": "Expense not found with id: xxx"
}
```

**409 Duplicate:**
```json
{
  "success": false,
  "message": "User already exists with username: 'johndoe'"
}
```

---

## 🔒 Security

- **JWT Authentication** - Secure token-based auth
- **BCrypt Encryption** - Password hashing
- **Role-Based Access** - USER and ADMIN roles
- **Input Validation** - Request validation
- **CORS Configuration** - Cross-origin settings

### Token Usage

1. Register or login to get tokens
2. Add to requests: `Authorization: Bearer <token>`
3. Tokens expire after 24 hours

---

## 📦 Build & Deploy

```bash
# Build JAR
mvn clean package -DskipTests

# Run JAR
java -jar target/expense-tracker-1.0.0.jar

# Docker (if available)
docker build -t expense-tracker:latest .
docker run -p 8080:8080 expense-tracker:latest
```

---

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Sachith**
- GitHub: [@Sachith77](https://github.com/Sachith77)

---

<p align="center">
  Made with ❤️ using Spring Boot
</p>
