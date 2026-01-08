# Investment_Banking_Backend

A secure, scalable **Spring Boot backend** for the Investment Banking Deal Pipeline Management System. This backend exposes RESTful APIs to manage users, deals, and collaboration notes with **JWT-based authentication** and **role-based authorization**.

---

## 📌 Problem Statement

Investment banking teams manage multiple high-value deals across complex lifecycle stages. Traditional tools like spreadsheets and emails introduce:

* Data inconsistency and duplication
* Lack of role-based access control
* Exposure of sensitive financial data
* No structured audit or lifecycle tracking

---

## ✅ Backend Solution Overview

This backend provides a **secure, centralized system** that:

1. Centralizes all deal and user data
2. Implements JWT-based authentication
3. Enforces role-based authorization (USER / ADMIN)
4. Secures sensitive deal information
5. Supports structured collaboration via notes
6. Ensures scalability and maintainability

---

## 🧱 Technology Stack

### Backend

| Layer                 | Technology                  |
| --------------------- | --------------------------- |
| Language              | Java 17                     |
| Framework             | Spring Boot                 |
| Security              | Spring Security + JWT       |
| ORM                   | Spring Data JPA (Hibernate) |
| Database              | MySQL                       |
| Build Tool            | Maven                       |
| Boilerplate Reduction | Lombok                      |

---

## 🏗️ System Architecture (Backend)

The backend follows a **layered architecture** with clear separation of concerns.

```
┌─────────────────────────────────────────────────────────────┐
│                    SERVER LAYER                             │
│                    localhost:8080                           │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│   ┌─────────────────────────────────────────────────────┐   │
│   │              SPRING BOOT APPLICATION                │   │
│   │                                                     │   │
│   │  ┌─────────────────────────────────────────────┐   │   │
│   │  │       SPRING SECURITY FILTER CHAIN           │   │   │
│   │  │  • JWT Authentication Filter                │   │   │
│   │  │  • Role-Based Authorization                 │   │   │
│   │  │  • CORS Configuration                       │   │   │
│   │  └─────────────────────────────────────────────┘   │   │
│   │                                                     │   │
│   │  ┌─────────────────────────────────────────────┐   │   │
│   │  │            REST CONTROLLERS                 │   │   │
│   │  │  AuthController | DealController | Admin   │   │   │
│   │  └─────────────────────────────────────────────┘   │   │
│   │                                                     │   │
│   │  ┌─────────────────────────────────────────────┐   │   │
│   │  │              SERVICE LAYER                  │   │   │
│   │  │  Business Logic | Validation | Transactions │   │   │
│   │  └─────────────────────────────────────────────┘   │   │
│   │                                                     │   │
│   │  ┌─────────────────────────────────────────────┐   │   │
│   │  │             REPOSITORY LAYER                │   │   │
│   │  │       Spring Data JPA (Hibernate)           │   │   │
│   │  └─────────────────────────────────────────────┘   │   │
│   └───────────────────────────┬─────────────────────────┘   │
│                               │ JDBC / Hibernate             │
└───────────────────────────────┼─────────────────────────────┘
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    DATA LAYER                               │
│                    MySQL DATABASE                           │
├─────────────────────────────────────────────────────────────┤
│   USERS (id, username, password, role)                      │
│   DEALS (id, client_name, stage, value)                     │
│   NOTES (id, deal_id, user_id, note)                        │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔐 Security Design

* Stateless authentication using **JWT**
* Password encryption using **BCrypt**
* Role-based authorization using Spring Security
* Secure REST endpoints with `@PreAuthorize`
* CORS configured for Angular frontend

---

## 🔄 Authentication & Authorization Flow

1. User sends login request with credentials
2. Backend validates credentials from database
3. JWT token is generated upon successful authentication
4. Token is returned to the frontend
5. Frontend sends JWT in Authorization header
6. JWT filter validates token for every request
7. Access granted based on user role

---

## 📡 REST API Modules

### Auth APIs

* `POST /api/auth/login` – Authenticate user & generate JWT
* `POST /api/auth/register` – Register new user (Admin only)

### Deal APIs

* `GET /api/deals` – Fetch deals (role-based visibility)
* `POST /api/deals` – Create a new deal
* `PUT /api/deals/{id}` – Update deal stage or details
* `DELETE /api/deals/{id}` – Delete deal (Admin only)

### Notes APIs

* `GET /api/deals/{id}/notes` – Fetch deal notes
* `POST /api/deals/{id}/notes` – Add collaboration notes

### Admin APIs

* `GET /api/admin/users` – View all users
* `PUT /api/admin/users/{id}/role` – Update user role

---

## 🚀 Running the Application

### Prerequisites

* Java 17
* Maven
* MySQL Server

### Steps

```bash
# Clone the repository
git clone <repository-url>

# Navigate to backend directory
cd Investment_Banking_Backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

Backend will start at:

```
http://localhost:8080
```

---

## 🗄️ Database Configuration

Update `application.properties` or `application.yml`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/investment_banking
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 📈 Key Backend Features

* Clean layered architecture
* Secure JWT-based authentication
* Role-based access control (RBAC)
* Transaction management
* Scalable REST APIs
* Easy integration with Angular frontend

---

## 📚 Additional Resources

* [Spring Boot Documentation](https://spring.io/projects/spring-boot)
* [Spring Security](https://spring.io/projects/spring-security)
* [JWT Introduction](https://jwt.io/introduction)

---

## Imp login credientials
Admin Accounts:
1. Username: admin       Password: admin123
2. Username: bob.wilson  Password: password

User Accounts:
1. Username: john.doe    Password: user123
2. Username: jane.smith  Password: password

### ✨ This backend is designed to meet enterprise-level security, scalability, and maintainability requirements for modern investment banking applications.
