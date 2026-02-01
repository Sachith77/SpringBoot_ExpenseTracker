# Expense Tracker API

A simple REST API for tracking personal expenses built with Spring Boot and MongoDB.

## Tech Stack
- Java 17
- Spring Boot 3.2.2
- Spring Security with JWT
- MongoDB Atlas

## API Endpoints (10 Total)

### Authentication (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login user |

### Expenses (Requires Auth)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/expenses` | Create expense |
| GET | `/api/expenses` | Get all expenses |
| PUT | `/api/expenses/{id}` | Update expense |
| DELETE | `/api/expenses/{id}` | Delete expense |

### Categories (Requires Auth)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/categories` | Create category |
| GET | `/api/categories` | Get all categories |

### Analytics (Requires Auth)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/analytics/summary` | Get expense summary |

### User (Requires Auth)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users/me` | Get current user profile |

## Request Examples

### Register
```json
POST /api/auth/register
{
    "firstName": "John",
    "lastName": "Doe",
    "username": "johndoe",
    "email": "john@example.com",
    "password": "password123"
}
```

### Login
```json
POST /api/auth/login
{
    "usernameOrEmail": "johndoe",
    "password": "password123"
}
```

### Create Category
```json
POST /api/categories
Authorization: Bearer <token>
{
    "name": "Food",
    "description": "Food and dining expenses"
}
```

### Create Expense (6)
```json
POST /api/expenses
Authorization: Bearer <token>
{
    "title": "Dinner",
    "description": "Dinner at restaurant",
    "amount": 45.00,
    "categoryId": "<category-id>",
    "expenseType": "EXPENSE",
    "expenseDate": "2026-01-31"
}
```

### Update Expense (8)
```json
PUT /api/expenses/{id}
Authorization: Bearer <token>
{
    "title": "Dinner",
    "description": "Dinner at restaurant",
    "amount": 45.00,
    "categoryId": "<category-id>",
    "expenseType": "EXPENSE",
    "expenseDate": "2026-01-31"
}
```

### Delete Expense (9)
```
DELETE /api/expenses/{id}
Authorization: Bearer <token>
```

## Running the Application

```bash
cd expense-tracker
mvn spring-boot:run
```

Server runs at `http://localhost:8080`
