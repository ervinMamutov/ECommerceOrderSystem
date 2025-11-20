package com.example.e_commerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Product name cannot be blank")
  @Size(min = 3, max = 200,
  message = "Product name must be between 3 and 200 characters")
  private String name;

  @NotBlank(message = "Description cannot be blank")
  @Size(min = 3, max = 2000,
  message = "Description must be between 3 and 2000 characters")
  private String description;

  @NotNull(message = "Price cannot be null")
  @DecimalMin(value = "0.01", message = "Price must be greater then 0")
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @NotNull(message = "Stock quantity cannot be null")
  @Min(value = 0, message = "Stock quantity cannot be negative")
  @Column(nullable = false)
  private int stockQuantity;

  // Stock Keeping Unit
  @Column(unique = true, length = 50)
  private String sku;

 @NotBlank(message = "Category cannot be blank")
 @Size(min = 3, max = 100,
 message = "Category must be between 3 and 100 characters")
  private String category;

 @Column(nullable = false, columnDefinition = "boolean default true")
  private boolean active;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public Product() {}

  public Product(String name, String description, BigDecimal price,
                 int stockQuantity, String sku, String category, boolean active) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.sku = sku;
    this.category = category;
    this.active = true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", stock=" + stockQuantity +
            ", sku=" + sku + '\'' +
            " active+" + active +
            "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if(!(o instanceof Product)) return false;
    Product product = (Product) o;
    return Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
