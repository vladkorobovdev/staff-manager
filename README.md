# 🏢 Staff Manager API

![Java](https://img.shields.io/badge/Java-21%2B-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)

**Staff Manager** is a robust and scalable REST API service designed for automating employee and organizational structure management.

This project demonstrates modern backend development practices using **Java (Spring Boot 3)**, featuring a secure **JWT-based authentication system**, optimized database interactions, and a clean, layered architecture.

---

## 🚀 Key Features

### 🔐 Security & Authentication
* **JWT Implementation:** Secure authentication using a dual-token system: **Access Token** (short-lived) + **Refresh Token** (persisted in DB).
* **RBAC (Role-Based Access Control):** Granular permission management:
    * **ADMIN:** Full control over departments, employees, salaries, and roles.
    * **USER:** Access to personal profile, secure password change, and self-management.
* **Security Best Practices:** BCrypt password hashing, Stateless session management, and secure logout (Refresh Token revocation).

### 👥 Staff Management
* Comprehensive **CRUD operations** for Departments and Employees.
* **Data Validation:** Strict input validation using Jakarta Validation API (`@Valid`).
* **Business Logic:** Prevention of logical errors (e.g., unique email checks, protection against deleting departments with active employees).

### ⚙️ Technical Highlights
* **API Documentation:** Fully documented endpoints via **Swagger/OpenAPI**.
* **Database Migrations:** Version control for database schema using **Liquibase**.
* **DTO Mapping:** Efficient entity-to-DTO mapping using **MapStruct** to prevent over-fetching and sensitive data exposure.
* **Error Handling:** Centralized exception handling via `@RestControllerAdvice` providing meaningful JSON error responses.

---

## 🛠 Tech Stack

* **Core:** Java 17+, Spring Boot 3.x
* **Database:** PostgreSQL, Spring Data JPA
* **Migrations:** Liquibase
* **Security:** Spring Security, Java JWT (jjwt)
* **DevOps:** Docker, Docker Compose
* **Utilities:** Lombok, MapStruct, Maven
* **Documentation:** Springdoc OpenAPI (Swagger UI)

---

## 🏗 Architecture & Design Decisions

The project focuses on code quality, scalability, and performance:

1.  **Solving the N+1 Problem:** Optimized JPA queries using `@EntityGraph` and `JOIN FETCH` to efficiently load related entities (Departments + Employees).
2.  **Transactional Integrity:** All data-modifying operations are wrapped in `@Transactional` to ensure ACID compliance.
3.  **Clean Architecture:** Strict separation of concerns (Controller → Service → Repository).
4.  **Safe Updates:** Dedicated DTOs for different update scenarios (e.g., separate endpoints for Profile Update vs. Password Change).

---

## 💾 Database Schema

The project uses a normalized relational database structure:

* **Departments:** `id`, `name` (One-to-Many relationship with Employees).
* **Employees:** `id`, `email`, `password_hash`, `role`, `salary`, `department_id`.
* **RefreshTokens:** `id`, `token`, `expiry_date`, `employee_id` (One-to-One relationship with Employees).

---

## 🚀 Getting Started

### Prerequisites
* Docker & Docker Compose
* (Optional) JDK 17+ and Maven for local development

### Quick Start (Docker)

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/vladkorobovdev/staff-manager.git](https://github.com/vladkorobovdev/staff-manager.git)
    cd staff-manager
    ```

2.  **Start the application and database:**
    ```bash
    docker-compose up -d
    ```

3.  **Access the application:** The API will be available at `http://localhost:8080`.

### Default Admin Credentials

On the first launch, the application automatically initializes an Admin user (via `DataInitializer`) if the database is empty:

* **Email:** `admin@staff.com`
* **Password:** `admin`

---

## 📖 API Documentation & Testing

The project implements the **OpenAPI 3.0** specification using `springdoc-openapi`. The interactive Swagger UI allows you to explore endpoints, view DTO schemas, and test requests directly from the browser.

### 🔗 Accessing Swagger UI
Once the application is running, navigate to:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### 🔐 How to Authenticate
Most endpoints are secured. To test them within Swagger UI:

1.  **Get a Token:** Expand the `auth-controller` section and execute `POST /auth/login` with valid credentials (e.g., the default admin).
2.  **Copy the Access Token:** Copy the `accessToken` string from the response body.
3.  **Authorize:**
    * Click the green **Authorize** button at the top right of the Swagger page.
    * In the "Value" field, enter the token.
    * Click **Authorize** and then **Close**.
4.  **Test:** Now you can execute protected requests (e.g., `GET /api/employees`) directly from the UI.


---

## 🔮 Roadmap

* [ ] Setup CI/CD pipeline (GitHub Actions).
* [ ] Implement Audit Logging for administrative actions.

---

## 📞 Contact

**Uladzislau Korabau** — Java Backend Developer

* 📧 Email: [vladkorobov.work@gmail.com]
* 💼 LinkedIn: [https://www.linkedin.com/in/uladzislau-korabau-675860265/]

---
*This project was developed for educational purposes and is open for suggestions.*
