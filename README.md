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
- [Configuration](#-configuration)
- [API Documentation](#-api-documentation)
  - [Authentication](#1-authentication-endpoints)
  - [Expenses](#2-expense-endpoints)
  - [Categories](#3-category-endpoints)
  - [Analytics](#4-analytics-endpoints)
  - [User Profile](#5-user-profile-endpoints)
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

## ⚙ Configuration

| Property | Description | Default |
|----------|-------------|---------|
| `server.port` | Application port | `8080` |
| `spring.data.mongodb.uri` | MongoDB connection string | Required |
| `jwt.secret` | JWT signing secret | Required |
| `jwt.expiration` | Access token expiry (ms) | `86400000` |

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication
All endpoints except `/api/auth/**` require a valid JWT token in the Authorization header:
```
Authorization: Bearer <your_access_token>
```

---

## 1. Authentication Endpoints

### 1.1 Register User

Creates a new user account.

```http
POST /api/auth/register
```

**Request Body:**
```json
{
    "username": "johndoe",
    "email": "john.doe@example.com",
    "password": "SecurePass123!",
    "firstName": "John",
    "lastName": "Doe"
}
```

| Field | Type | Required | Validation |
|-------|------|----------|------------|
| `username` | string | ✅ | 3-50 characters |
| `email` | string | ✅ | Valid email format, max 100 chars |
| `password` | string | ✅ | 8-100 characters |
| `firstName` | string | ❌ | Max 50 characters |
| `lastName` | string | ❌ | Max 50 characters |

**Success Response:** `201 Created`
```json
{
    "success": true,
    "message": "User registered successfully",
    "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "tokenType": "Bearer",
        "expiresIn": 86400000,
        "user": {
            "id": "65a1b2c3d4e5f6g7h8i9j0k1",
            "username": "johndoe",
            "email": "john.doe@example.com",
            "firstName": "John",
            "lastName": "Doe",
            "role": "USER"
        }
    },
    "timestamp": "2026-02-01T10:30:00"
}
```

---

### 1.2 Login User

Authenticates a user and returns JWT tokens.

```http
POST /api/auth/login
```

**Request Body:**
```json
{
    "usernameOrEmail": "johndoe",
    "password": "SecurePass123!"
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `usernameOrEmail` | string | ✅ | Username or email address |
| `password` | string | ✅ | User password |

**Success Response:** `200 OK`
```json
{
    "success": true,
    "message": "Login successful",
    "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "tokenType": "Bearer",
        "expiresIn": 86400000,
        "user": {
            "id": "65a1b2c3d4e5f6g7h8i9j0k1",
            "username": "johndoe",
            "email": "john.doe@example.com",
            "firstName": "John",
            "lastName": "Doe",
            "role": "USER"
        }
    },
    "timestamp": "2026-02-01T10:30:00"
}
```

---

## 2. Expense Endpoints

### 2.1 Create Expense

Creates a new expense or income entry.

```http
POST /api/expenses
Authorization: Bearer <token>
```

**Request Body:**
```json
{
    "title": "Grocery Shopping",
    "description": "Weekly groceries from supermarket",
    "amount": 150.50,
    "expenseType": "EXPENSE",
    "expenseDate": "2026-02-01",
    "categoryId": "65a1b2c3d4e5f6g7h8i9j0k1",
    "notes": "Bought fruits and vegetables",
    "recurring": false,
    "recurringFrequency": null
}
```

| Field | Type | Required | Validation |
|-------|------|----------|------------|
| `title` | string | ✅ | Max 100 characters |
| `description` | string | ❌ | Max 500 characters |
| `amount` | decimal | ✅ | Must be > 0, max 17 integer digits, 2 decimal |
| `expenseType` | enum | ✅ | `EXPENSE` or `INCOME` |
| `expenseDate` | date | ✅ | Format: `YYYY-MM-DD` |
| `categoryId` | string | ❌ | Valid category ID |
| `notes` | string | ❌ | Max 500 characters |
| `recurring` | boolean | ❌ | Default: `false` |
| `recurringFrequency` | string | ❌ | Max 20 characters (e.g., "WEEKLY", "MONTHLY") |

**Success Response:** `201 Created`
```json
{
    "success": true,
    "message": "Expense created successfully",
    "data": {
        "id": "65a1b2c3d4e5f6g7h8i9j0k2",
        "title": "Grocery Shopping",
        "description": "Weekly groceries from supermarket",
        "amount": 150.50,
        "expenseType": "EXPENSE",
        "expenseDate": "2026-02-01",
        "category": {
            "id": "65a1b2c3d4e5f6g7h8i9j0k1",
            "name": "Food & Dining",
            "description": "Food related expenses",
            "color": "#FF5733",
            "icon": "restaurant",
            "isDefault": false,
            "expenseCount": 15,
            "createdAt": "2026-01-15T08:00:00",
            "updatedAt": "2026-01-15T08:00:00"
        },
        "receiptUrl": null,
        "receiptFilename": null,
        "notes": "Bought fruits and vegetables",
        "recurring": false,
        "recurringFrequency": null,
        "createdAt": "2026-02-01T10:30:00",
        "updatedAt": "2026-02-01T10:30:00"
    },
    "timestamp": "2026-02-01T10:30:00"
}
```

---

### 2.2 Get All Expenses

Retrieves all expenses for the authenticated user.

```http
GET /api/expenses
Authorization: Bearer <token>
```

**Success Response:** `200 OK`
```json
{
    "success": true,
    "message": null,
    "data": [
        {
            "id": "65a1b2c3d4e5f6g7h8i9j0k2",
            "title": "Grocery Shopping",
            "description": "Weekly groceries from supermarket",
            "amount": 150.50,
            "expenseType": "EXPENSE",
            "expenseDate": "2026-02-01",
            "category": {
                "id": "65a1b2c3d4e5f6g7h8i9j0k1",
                "name": "Food & Dining",
                "color": "#FF5733",
                "icon": "restaurant"
            },
            "notes": "Bought fruits and vegetables",
            "recurring": false,
            "createdAt": "2026-02-01T10:30:00",
            "updatedAt": "2026-02-01T10:30:00"
        }
    ],
    "timestamp": "2026-02-01T10:30:00"
}
```

---

### 2.3 Update Expense

Updates an existing expense.

```http
PUT /api/expenses/{id}
Authorization: Bearer <token>
```

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | string | Expense ID |

**Request Body:**
```json
{
    "title": "Grocery Shopping - Updated",
    "description": "Weekly groceries from local market",
    "amount": 175.00,
    "expenseType": "EXPENSE",
    "expenseDate": "2026-02-01",
    "categoryId": "65a1b2c3d4e5f6g7h8i9j0k1",
    "notes": "Added some organic products",
    "recurring": false
}
```

**Success Response:** `200 OK`
```json
{
    "success": true,
    "message": "Expense updated successfully",
    "data": {
        "id": "65a1b2c3d4e5f6g7h8i9j0k2",
        "title": "Grocery Shopping - Updated",
        "amount": 175.00,
        "updatedAt": "2026-02-01T11:00:00"
    },
    "timestamp": "2026-02-01T11:00:00"
}
```

---

### 2.4 Delete Expense

Deletes an expense.

```http
DELETE /api/expenses/{id}
Authorization: Bearer <token>
```

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | string | Expense ID |

**Success Response:** `200 OK`
```json
{
    "success": true,
    "message": "Expense deleted successfully",
    "data": null,
    "timestamp": "2026-02-01T11:00:00"
}
```

---

## 3. Category Endpoints

### 3.1 Create Category

Creates a new expense category.

```http
POST /api/categories
Authorization: Bearer <token>
```

**Request Body:**
```json
{
    "name": "Transportation",
    "description": "Travel and commute expenses",
    "color": "#3498DB",
    "icon": "directions_car"
}
```

| Field | Type | Required | Validation |
|-------|------|----------|------------|
| `name` | string | ✅ | Max 50 characters |
| `description` | string | ❌ | Max 255 characters |
| `color` | string | ❌ | Valid hex code (e.g., `#FF5733`), max 7 chars |
| `icon` | string | ❌ | Icon name, max 50 characters |

**Success Response:** `201 Created`
```json
{
    "success": true,
    "message": "Category created successfully",
    "data": {
        "id": "65a1b2c3d4e5f6g7h8i9j0k3",
        "name": "Transportation",
        "description": "Travel and commute expenses",
        "color": "#3498DB",
        "icon": "directions_car",
        "isDefault": false,
        "expenseCount": 0,
        "createdAt": "2026-02-01T10:30:00",
        "updatedAt": "2026-02-01T10:30:00"
    },
    "timestamp": "2026-02-01T10:30:00"
}
```

---

### 3.2 Get All Categories

Retrieves all categories for the authenticated user.

```http
GET /api/categories
Authorization: Bearer <token>
```

**Success Response:** `200 OK`
```json
{
    "success": true,
    "message": null,
    "data": [
        {
            "id": "65a1b2c3d4e5f6g7h8i9j0k1",
            "name": "Food & Dining",
            "description": "Food related expenses",
            "color": "#FF5733",
            "icon": "restaurant",
            "isDefault": true,
            "expenseCount": 15,
            "createdAt": "2026-01-15T08:00:00",
            "updatedAt": "2026-01-15T08:00:00"
        },
        {
            "id": "65a1b2c3d4e5f6g7h8i9j0k3",
            "name": "Transportation",
            "description": "Travel and commute expenses",
            "color": "#3498DB",
            "icon": "directions_car",
            "isDefault": false,
            "expenseCount": 8,
            "createdAt": "2026-02-01T10:30:00",
            "updatedAt": "2026-02-01T10:30:00"
        }
    ],
    "timestamp": "2026-02-01T10:30:00"
}
```

---

## 4. Analytics Endpoints

### 4.1 Get Summary

Retrieves expense analytics and summary for the authenticated user.

```http
GET /api/analytics/summary
Authorization: Bearer <token>
```

**Success Response:** `200 OK`
```json
{
    "success": true,
    "message": null,
    "data": {
        "totalExpenses": 2500.75,
        "totalIncome": 5000.00,
        "balance": 2499.25,
        "expenseCount": 25,
        "categoryCount": 5,
        "expensesByCategory": {
            "Food & Dining": 850.50,
            "Transportation": 450.00,
            "Entertainment": 300.25,
            "Utilities": 500.00,
            "Shopping": 400.00
        }
    },
    "timestamp": "2026-02-01T10:30:00"
}
```

**Response Fields:**
| Field | Type | Description |
|-------|------|-------------|
| `totalExpenses` | decimal | Sum of all expenses |
| `totalIncome` | decimal | Sum of all income entries |
| `balance` | decimal | Income minus expenses |
| `expenseCount` | integer | Total number of expense records |
| `categoryCount` | integer | Number of categories used |
| `expensesByCategory` | object | Breakdown of expenses by category name |

---

## 5. User Profile Endpoints

### 5.1 Get Current User

Retrieves the profile of the authenticated user.

```http
GET /api/users/me
Authorization: Bearer <token>
```

**Success Response:** `200 OK`
```json
{
    "success": true,
    "message": null,
    "data": {
        "id": "65a1b2c3d4e5f6g7h8i9j0k1",
        "username": "johndoe",
        "email": "john.doe@example.com",
        "firstName": "John",
        "lastName": "Doe",
        "role": "USER",
        "monthlyBudget": 3000.00,
        "createdAt": "2026-01-01T00:00:00",
        "updatedAt": "2026-02-01T10:00:00"
    },
    "timestamp": "2026-02-01T10:30:00"
}
```

---

## ⚠️ Error Handling

All errors follow a consistent format:

```json
{
    "success": false,
    "message": "Error description",
    "error": {
        "code": "ERROR_CODE",
        "details": "Detailed error message",
        "timestamp": "2026-02-01T10:30:00",
        "path": "/api/expenses"
    }
}
```

### HTTP Status Codes

| Status Code | Description |
|-------------|-------------|
| `200` | OK - Request successful |
| `201` | Created - Resource created successfully |
| `400` | Bad Request - Invalid request data |
| `401` | Unauthorized - Invalid or missing token |
| `403` | Forbidden - Insufficient permissions |
| `404` | Not Found - Resource not found |
| `409` | Conflict - Duplicate resource |
| `422` | Unprocessable Entity - Validation failed |
| `500` | Internal Server Error - Server error |

### Validation Errors

```json
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {
            "field": "email",
            "message": "Email must be valid"
        },
        {
            "field": "password",
            "message": "Password must be between 8 and 100 characters"
        }
    ],
    "timestamp": "2026-02-01T10:30:00"
}
```

---

## 🔒 Security

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **Role-Based Access**: USER and ADMIN roles
- **Input Validation**: Comprehensive request validation
- **CORS Configuration**: Configurable cross-origin settings

### Token Usage

1. Register or login to receive tokens
2. Include the access token in subsequent requests:
   ```
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```
3. Use refresh token to obtain new access token when expired

---

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with coverage report
mvn test jacoco:report
```

---

## 📦 Build & Deploy

```bash
# Build JAR file
mvn clean package -DskipTests

# Run JAR
java -jar target/expense-tracker-1.0.0.jar

# Docker build (if Dockerfile exists)
docker build -t expense-tracker:latest .
docker run -p 8080:8080 expense-tracker:latest
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Sachith**
- GitHub: [@Sachith77](https://github.com/Sachith77)

---

<p align="center">
  Made with ❤️ using Spring Boot
</p>
