# Data Structure Proposal for E-Commerce Order System

This document defines the entity model and relationships for the e-commerce application.

---

## Core Entity Model

### 1. Product (Catalog Management)

```
Fields:
- id: Long (Primary Key, Auto-generated)
- name: String (Product name, max 200 chars)
- description: String (Detailed description, max 2000 chars)
- price: BigDecimal (Current price, precision 10, scale 2)
- stockQuantity: Integer (Available inventory count)
- sku: String (Stock Keeping Unit, unique, max 50 chars)
- category: String (Category name, max 100 chars)
- active: Boolean (Soft delete flag)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime

Indexes: sku (unique), name, category
Teaches: Basic JPA entity, validation, BigDecimal for money
```

### 2. Category (Product Organization)

```
Fields:
- id: Long (Primary Key)
- name: String (Category name, max 100 chars)
- description: String (max 500 chars)
- parentCategory: Category (Self-referential Many-to-One, for hierarchy)
- products: List<Product> (One-to-Many, back reference)
- active: Boolean
- createdAt: LocalDateTime

Indexes: name (unique)
Teaches: Self-referential relationships, tree structures
```

### 3. User (Customer Accounts)

```
Fields:
- id: Long (Primary Key)
- email: String (Unique, max 255 chars, valid email format)
- passwordHash: String (Hashed password, never store plain text!)
- firstName: String (max 100 chars)
- lastName: String (max 100 chars)
- phoneNumber: String (max 20 chars)
- role: UserRole enum (CUSTOMER, ADMIN)
- orders: List<Order> (One-to-Many)
- addresses: List<Address> (One-to-Many)
- cart: Cart (One-to-One)
- active: Boolean
- createdAt: LocalDateTime
- lastLoginAt: LocalDateTime

Indexes: email (unique)
Teaches: Security concerns, enums, user management
```

### 4. UserRole (Enum)

```
Values: CUSTOMER, ADMIN, GUEST
Teaches: Java enums, role-based authorization
```

### 5. Address (Shipping/Billing Addresses)

```
Fields:
- id: Long (Primary Key)
- user: User (Many-to-One)
- addressType: AddressType enum (SHIPPING, BILLING)
- fullName: String (max 200 chars)
- streetAddress: String (max 255 chars)
- city: String (max 100 chars)
- state: String (max 100 chars)
- postalCode: String (max 20 chars)
- country: String (max 100 chars)
- isDefault: Boolean
- createdAt: LocalDateTime

Teaches: Embedded value objects, address validation
```

### 6. AddressType (Enum)

```
Values: SHIPPING, BILLING, BOTH
```

### 7. Order (Purchase Records)

```
Fields:
- id: Long (Primary Key)
- orderNumber: String (Unique, generated, format: ORD-YYYYMMDD-XXXXX)
- user: User (Many-to-One)
- orderItems: List<OrderItem> (One-to-Many, cascade ALL, orphanRemoval)
- status: OrderStatus enum
- totalAmount: BigDecimal (Calculated from order items)
- shippingAddress: Address (Many-to-One)
- billingAddress: Address (Many-to-One)
- payment: Payment (One-to-One)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
- paidAt: LocalDateTime (nullable)
- shippedAt: LocalDateTime (nullable)
- deliveredAt: LocalDateTime (nullable)

Indexes: orderNumber (unique), user_id, status, createdAt
Teaches: Aggregate root, cascade operations, business workflow
```

### 8. OrderStatus (Enum)

```
Values: PENDING, PAYMENT_PROCESSING, PAID, SHIPPED, DELIVERED, CANCELLED, REFUNDED

Teaches: State machine pattern, business status tracking
```

### 9. OrderItem (Order Line Items)

```
Fields:
- id: Long (Primary Key)
- order: Order (Many-to-One)
- product: Product (Many-to-One)
- quantity: Integer (Must be > 0)
- priceAtPurchase: BigDecimal (Snapshot of price when ordered)
- subtotal: BigDecimal (quantity × priceAtPurchase)
- createdAt: LocalDateTime

Indexes: order_id, product_id
Teaches: Value objects, price history, calculated fields
```

### 10. Cart (Shopping Cart)

```
Fields:
- id: Long (Primary Key)
- user: User (One-to-One)
- cartItems: List<CartItem> (One-to-Many, cascade ALL, orphanRemoval)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime

Teaches: Session management, temporary data
```

### 11. CartItem (Cart Line Items)

```
Fields:
- id: Long (Primary Key)
- cart: Cart (Many-to-One)
- product: Product (Many-to-One)
- quantity: Integer (Must be > 0)
- addedAt: LocalDateTime

Indexes: cart_id, product_id
Constraint: Unique(cart_id, product_id) - prevent duplicate products in cart
Teaches: Uniqueness constraints, composite keys
```

### 12. Payment (Payment Records)

```
Fields:
- id: Long (Primary Key)
- order: Order (One-to-One)
- paymentMethod: PaymentMethod enum
- amount: BigDecimal
- status: PaymentStatus enum
- transactionId: String (From payment gateway, max 255 chars)
- gatewayResponse: String (JSON response from gateway, TEXT type)
- paidAt: LocalDateTime
- createdAt: LocalDateTime

Indexes: transactionId (unique), order_id
Teaches: External integration, payment processing, audit trails
```

### 13. PaymentMethod (Enum)

```
Values: CREDIT_CARD, DEBIT_CARD, PAYPAL, STRIPE, BANK_TRANSFER
```

### 14. PaymentStatus (Enum)

```
Values: PENDING, AUTHORIZED, CAPTURED, FAILED, REFUNDED, CANCELLED
```

---

## Entity Relationship Diagram

```
User (1) ----< (M) Order
User (1) ---- (1) Cart
User (1) ----< (M) Address

Order (1) ----< (M) OrderItem
Order (M) >---- (1) Address [shipping]
Order (M) >---- (1) Address [billing]
Order (1) ---- (1) Payment

OrderItem (M) >---- (1) Product

Cart (1) ----< (M) CartItem
CartItem (M) >---- (1) Product

Product (M) >---- (1) Category
Category (1) ----< (M) Category [self-referential]
```

**Legend:**
- `(1)` = One
- `(M)` = Many
- `----<` = One-to-Many
- `>----` = Many-to-One
- `----` = One-to-One

---

## Implementation Plan

### Phase 1: Foundation Entities (Start Here)
1. **Product** - Simplest entity, no complex relationships initially
2. **Category** - Add after Product, teaches self-referential relationships
3. Link Product ↔ Category

### Phase 2: User Management
4. **UserRole** enum
5. **User** - Core user entity
6. **AddressType** enum
7. **Address** - Link to User

### Phase 3: Order Management (Core Business Logic)
8. **OrderStatus** enum
9. **Order** entity
10. **OrderItem** entity
11. Link Order → User, Order → OrderItems → Product

### Phase 4: Shopping Cart
12. **Cart** entity
13. **CartItem** entity
14. Link Cart → User, CartItems → Product

### Phase 5: Payment Integration
15. **PaymentMethod** enum
16. **PaymentStatus** enum
17. **Payment** entity
18. Link Payment → Order

---

## Key Design Decisions

### 1. Why separate Cart and Order?
- Cart = temporary, mutable (user can change quantities)
- Order = permanent, immutable (historical record)
- Teaches: Domain boundaries, state transitions

### 2. Why store `priceAtPurchase` in OrderItem?
- Product prices change over time
- Orders must reflect the price at purchase time
- Teaches: Historical data integrity, audit requirements

### 3. Why BigDecimal for money?
- Float/Double have precision issues (0.1 + 0.2 ≠ 0.3 in binary)
- Financial calculations require exact arithmetic
- Teaches: Critical importance of data types

### 4. Why separate Payment entity?
- Decouples payment processing from order management
- Allows multiple payment attempts per order
- Teaches: Single Responsibility Principle, external system integration

### 5. Why enums for status fields?
- Type safety (prevents invalid status values)
- Compile-time checking
- Self-documenting code
- Teaches: Type systems, domain constraints

### 6. Why cascade operations on OrderItems?
- OrderItems cannot exist without an Order (aggregate pattern)
- When Order is deleted, OrderItems should be deleted
- Teaches: Aggregate roots, entity lifecycle management

### 7. Why unique constraint on Cart + Product?
- Same product shouldn't appear twice in cart
- Update quantity instead of adding duplicate
- Teaches: Data integrity, database constraints

---

## What Each Phase Teaches

**Phase 1 (Product/Category):**
- Basic JPA entities
- @ManyToOne relationships
- Self-referential relationships
- Tree structures

**Phase 2 (User/Address):**
- Security concepts (password hashing)
- Email validation
- One-to-Many relationships
- User management patterns

**Phase 3 (Order/OrderItem):**
- Aggregate pattern
- Cascade operations
- Calculated fields
- Business workflows
- State machines (OrderStatus transitions)

**Phase 4 (Cart/CartItem):**
- Session management
- Temporary vs permanent data
- Composite uniqueness constraints

**Phase 5 (Payment):**
- External system integration
- Transaction management
- Audit trails
- Financial data handling
