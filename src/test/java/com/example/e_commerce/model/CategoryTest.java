package com.example.e_commerce.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {
  private Validator validator;

  @BeforeEach
  void setUp() {
    // Create validator for testing validation annotation
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Should create category with valid data")
  void shouldCreateCategoryWithValidData() {
    Category category =
            new Category("Electronics", "Electronic device", null, true);

    var violations = validator.validate(category);
    assertTrue(violations.isEmpty());
    assertEquals("Electronics", category.getName());
  }

  @Test
  @DisplayName("Should fail when name is blank")
  void shouldFailWhenNameIsBlank() {
    Category category = new Category("", "Description", null, true);

    var violations = validator.validate(category);
    assertFalse(violations.isEmpty());
  }

  @Test
  @DisplayName("Should fail when name too short")
  void shouldFailWhenNameTooShort() {
    Category category = new Category("AB", "Description", null, true);

    Set<ConstraintViolation<Category>> violations =
            validator.validate(category);

    assertFalse(violations.isEmpty());
  }

  @Test
  @DisplayName("Should fail when description is blank")
  void shouldFailWhenDescriptionIsBlank() {
    Category category = new Category("Valid", "", null, true );

    Set<ConstraintViolation<Category>> violations =
            validator.validate((category));

    assertFalse(violations.isEmpty());
  }

  @Test
  @DisplayName("Should have correct toString format")
  void sholdHaveCorrectToStringFormat() {
    Category category = new Category("Test", "Desc", null, true);
    category.setId(1L);

    String result = category.toString();

    assertTrue(result.contains("id=1"));
    assertTrue(result.contains("Test"));
  }

  @Test
  @DisplayName("Setters should update values correctly")
  void settersShouldUpdateValuesCorrectly() {
    Category category = new Category();
    category.setName("Update");
    category.setDescription("New Desc");
    category.setActive(false);

    assertEquals("Update", category.getName());
    assertEquals("New Desc", category.getDescription());
    assertFalse(category.isActive());
  }

  @Test
  @DisplayName("Product list should be empty by default")
  void productListShouldBeEmptyByDefault() {
    Category category = new Category("Test", "Desc", null, true);
    assertNotNull(category.getProducts());
    assertTrue(category.getProducts().isEmpty());
  }

  @Test
  @DisplayName("Should no equals with different ids")
  void shouldNoEqualsWithDifferentIds() {
    Category cat1 = new Category();
    cat1.setId(1L);
    Category cat2 = new Category();
    cat2.setId(2L);

    assertNotEquals(cat1, cat2);
  }

  @Test
  @DisplayName("Should allow null parent category")
  void shouldAllowNullParentCategory() {
    Category category = new Category("Root", "Root Category", null, true);

    assertNull(category.getParentCategory());

    Set<ConstraintViolation<Category>> violations =
            validator.validate(category);
    assertTrue(violations.isEmpty());
  }

  @Test
  @DisplayName("Should set parent category")
  void shouldSetParentCategory() {
    Category parent = new Category("Parent", "Parents Desc", null, true);

    Category child = new Category("Child", "Child Desc", parent, true);

    assertEquals(parent, child.getParentCategory());
  }

  @Test
  @DisplayName("Should test equals and hash code")
  void shouldTestEqualsAndHashCode() {
    Category cat1 = new Category("Test", "Desc", null, true);
    Category cat2 = new Category("Test", "Desc", null, true);

    // Without IDs, set, both have null id
    assertEquals(cat1, cat2);
    assertEquals(cat1.hashCode(), cat2.hashCode());
  }
}