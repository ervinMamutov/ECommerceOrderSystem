# E-Commerce Order System

Educational Java project for deep learning of Java, Spring Boot, JPA, and testing.

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.7**
- **PostgreSQL** (dev/prod)
- **H2** (testing)
- **Maven**

## Features

- Product catalog with inventory tracking
- Category hierarchy (self-referential)
- Shopping cart with session management
- Order workflow (pending → paid → shipped)
- Payment integration (stub)

## Quick Start

### Prerequisites
- Java 21+
- PostgreSQL
- Maven (or use included wrapper)

### Setup

1. Clone repository
2. Copy `.env.example` to `.env` and configure:
   ```
   DB_HOST=localhost
   DB_PORT=5432
   DB_NAME=ecommerce
   DB_USERNAME=your_user
   DB_PASSWORD=your_password
   ```

3. Run application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Commands

```bash
# Build
./mvnw clean package

# Run tests
./mvnw test

# Run specific test
./mvnw test -Dtest=ProductTest

# Run specific test method
./mvnw test -Dtest=ProductTest#shouldCreateProductWithValidData

# Run with profile
SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run
```

## Project Structure

```
src/main/java/com/example/e_commerce/
├── model/          # JPA entities
├── repository/     # Spring Data repositories
└── ...             # Services, controllers

src/test/java/      # Unit & integration tests
```

## Architecture

### Entity Relationships

```
User (1) ────< (M) Order
User (1) ──── (1) Cart
User (1) ────< (M) Address

Order (1) ────< (M) OrderItem
Order (M) >──── (1) Address [shipping]
Order (M) >──── (1) Address [billing]
Order (1) ──── (1) Payment

OrderItem (M) >──── (1) Product

Cart (1) ────< (M) CartItem
CartItem (M) >──── (1) Product

Product (M) >──── (1) Category
Category (1) ────< (M) Category [self-referential]
```

**Legend:**
- `(1)` = One
- `(M)` = Many
- `────<` = One-to-Many
- `>────` = Many-to-One
- `────` = One-to-One

## API Documentation

> **Note:** REST endpoints will be documented here as they are implemented.

### Planned Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | List all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |
| GET | `/api/categories` | List all categories |
| GET | `/api/cart` | Get current cart |
| POST | `/api/cart/items` | Add item to cart |
| POST | `/api/orders` | Create order from cart |

## Contributing

### Getting Started

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Make your changes
4. Run tests: `./mvnw test`
5. Commit with clear message: `git commit -m "feat: add your feature"`
6. Push and create a Pull Request

### Coding Standards

- Follow existing code style and formatting
- Use constructor-based dependency injection
- Use `BigDecimal` for all monetary values
- Add validation annotations to entity fields
- Implement `equals()`, `hashCode()`, `toString()` for entities
- Write unit tests for new functionality

### Commit Message Format

```
type: short description

- Detail 1
- Detail 2
```

Types: `feat`, `fix`, `test`, `docs`, `refactor`

## Educational Focus

This project emphasizes understanding:
- Core Java (OOP, collections, exceptions)
- JPA/Hibernate (entities, relationships, queries)
- Spring Boot (DI, annotations, configuration)
- Testing (JUnit, validation, mocking)
- Design patterns (Repository, Service layer, DTOs)

## License

MIT
