# 🎟️ Dynamic Event Ticketing - Backend Service

This is the core Spring Boot microservice for the Dynamic Event Ticketing System. It handles real-time seat management, concurrency-safe booking, and dynamic pricing logic.

## 🚀 Key Features

-   **Dynamic Pricing Engine**: Calculates price based on the *order* of tickets sold (not seat number).
    -   1-50: $50
    -   51-80: $75
    -   81-100: $100
-   **Concurrency & Data Integrity**: Uses **Pessimistic Locking** (`@Lock(LockModeType.PESSIMISTIC_WRITE)`) to ensure that pricing is accurate and seats are never double-booked during simultaneous high-volume requests.
-   **Automated Initialization**: A dedicated endpoint to reset the theater state for testing.
-   **Self-Documenting API**: Integrated with **Swagger/OpenAPI** for easy testing and frontend integration.

---

## 🛠️ Tech Stack

-   **Java 17** (JDK 17)
-   **Spring Boot 3.x** (Web, Data JPA)
-   **H2 Database** (In-memory for zero-configuration setup)
-   **Lombok** (Boilerplate reduction)
-   **Springdoc OpenAPI** (Swagger UI)

---

## 🚦 Getting Started

### 1. Prerequisites
-   Java 17 or higher
-   Maven 3.6+

### 2. Run the Application
1. Clone the repository.
2. From the root directory, run:
   ```bash
   mvn spring-boot:run
