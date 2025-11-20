package com.example.e_commerce.model;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
  private Validator validator;

  @BeforeEach
  void setUp() {
    // Create validator for testing validation annotation
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Should create product with valid data")
  void shouldCreateProductWithValidData() {
    // Arrange & Act
    Product product = new Product(
            "Gaming Laptop",
            "High performance gaming laptop",
            new BigDecimal("1299.99"),
            10,
            "LAPTOP-001",
            "Electronics",
            true
    );

    //Assert
    assertEquals("Gaming Laptop", product.getName());
    assertEquals(new BigDecimal("1299.99"), product.getPrice());
    assertEquals(10, product.getStockQuantity());
    assertTrue(product.isActive());
    assertNull(product.getId()); // ID is null before save
  }

  @Test
  @DisplayName("Should fail validation when name is blank")
  void shouldFailValidationWhenNameIsBlank() {
    //Arrange
    Product product = new Product(
            "", // Invalid: blank name
            "Description",
            new BigDecimal("100.00"),
            5,
            "SKU-100",
            "Category",
            true
    );

    //Act
    Set<ConstraintViolation<Product>> violations = validator.validate(product);

    //Assert
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage()
                    .contains("Product name cannot be blank")));
  }

  @Test
  @DisplayName("Should fail validation when name is too short")
  void shouldFailValidationWhenNameIsTooShort() {
    Product product = new Product(
            "AB", // Invalid: less than 3 characters
            "Descritpions",
            new BigDecimal("100.00"),
            5,
            "SKU-100",
            "Category",
            true
    );

    Set<ConstraintViolation<Product>> violations = validator.validate(product);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage()
                    .contains("between 3 and 200")));
  }

  @Test
  @DisplayName("Should fail validation when price is null")
  void shouldFailValidationWhenPriceIsNull() {
    Product product = new Product(
            "Valid Name",
            "Description",
            null, // Invalid: null price
            5,
            "SKU-100",
            "Category",
            true
    );

    Set<ConstraintViolation<Product>> violations =
            validator.validate((product));

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage()
                    .contains("Price cannot be null")));
  }

  @Test
  @DisplayName("Should have correct equals behavior")
  void shouldHaveCorrectEqualsBehavior() {
    Product product1 = new Product();
    product1.setId(1L);

    Product product2 = new Product();
    product2.setId(1L);

    Product product3 = new Product();
    product3.setId(3L);

    // Same ID = equal
    assertEquals(product1, product2);

    // Different ID = not equals
    assertNotEquals(product1, product3);

    // Same hashCode for equal objects
    assertEquals(product1.hashCode(), product2.hashCode());
  }

  @Test
  @DisplayName("Should have correct toString format")
  void shouldHaveCorrectToStringFomat() {
    Product product = new Product();
    product.setId(1L);
    product.setName("Test Product");
    product.setPrice(new BigDecimal("99.99"));

    String result =  product.toString();

    assertTrue(result.contains("id=1"));
    assertTrue(result.contains("Test Product"));
    assertTrue(result.contains("99.99"));
  }

  @Test
  @DisplayName("Setters should update values correctly")
  void settersShouldUpdateValuesCorrectly() {
    Product product = new Product();

    product.setName("New Name");
    product.setPrice(new BigDecimal("199.99"));
    product.setStockQuantity(100);
    product.setActive(false);

    assertEquals("New Name", product.getName());
    assertEquals(new BigDecimal("199.99"), product.getPrice());
    assertEquals(100, product.getStockQuantity());
    assertFalse(product.isActive());
  }
}
