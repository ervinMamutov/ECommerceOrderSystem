package com.example.e_commerce.model;


import com.example.e_commerce.enums.UserRole;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {
  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Should create valid user")
  void shouldCreateValidUser() {
    User user = new User("First name", "Last name", "email@email.com",
            "Phone number", "passwordHash", UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertTrue(violations.isEmpty());

    assertEquals("First name", user.getFirstName());
    assertEquals("Last name", user.getLastName());
    assertEquals("email@email.com", user.getEmail());
    assertEquals("Phone number", user.getPhoneNumber());
    assertEquals("passwordHash", user.getPasswordHash());
    assertTrue(user.isActive());
  }

  // Test for First name
  @Test
  @DisplayName("Should fail create valid user when first name is blank")
  void shouldFailCreateValidUserWhenFirstNameIsBlank() {
    User user = new User("", "Last name", "email@email.com", "Phone number",
            "passwordHash", UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(
            v -> v.getMessage().contains("First name cannot be blank")));
  }

  // Test for last name
  @Test
  @DisplayName(("Should Fail When First name too short"))
  void shouldFailWhenFirstNameTooShort() {
    User user = new User("Fi", "Last name", "we@fdfdf.com", "Phone number",
            "passwordHash", UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("First name must be between 3 and 100 characters")));
  }

  @Test
  @DisplayName("Should Fail when first name too long")
  void shouldFailWhenFirstNameTooLong() {
    User user = new User("qweqwe" +
            "sdwerfrtgyhujhytgfdrftghyrtrdgdffgfdfgfdfgdfghgdfdfgrytsdgdfgqweqwesdwerfrtgyhujhytgfdrftghyrtrdgdffgfdfgfdfgdfghgdfdfgrytsdgdfg",
            "Last name", "wefd@ee.df", "Phone number", "passwordHash",
            UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("First name must be between 3 and 100 characters")));
  }

  @Test
  @DisplayName("Should fail create user when last name is blank")
  void shouldFailCreateUserWhenLastNameIsBlank() {
    User user = new User("First name", "", "email@email.com", "Phone number",
            "passwordHash", UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(
            v -> v.getMessage().contains("Last name cannot be blank")));
  }

  @Test
  @DisplayName("Should Fall When Last name too short")
  void shouldFailWhenLastNameTooShort() {
    User user = new User("First name", "La", "", "Phone number", "passwordHash",
            UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("Last name must be between 3 and 100 characters")));
  }

  @Test
  @DisplayName("Should Fail when last name too long")
  void shouldFailWhenLastNameTooLong() {
    User user = new User("First name",
            "qweqwesdwerfrtgyhujhytgfdrftghyrtrdgdffgfdfgfdfgdfghgdfdfgrytsdgdfgqweqwesdwerfrtgyhujhytgfdrftghyrtrdgdffgfdfgfdfgdfghgdfdfgrytsdgdfg",
            "wefd@ee.df", "Phone number", "passwordHash", UserRole.CUSTOMER,
            true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("Last name must be between 3 and 100 characters")));
  }

  @Test
  @DisplayName("Should fail create user when email is blank")
  void shouldFailCreateUserWhenEmailIsBlank() {
    User user = new User("First name", "Last name", "", "Phone number",
            "passwordHash", UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Email cannot be blank")));
  }

  @Test
  @DisplayName("Should fail when email is invalid")
  void shouldFailWhenEmailIsInvalid() {
    User user = new User("First name", "Last Name", "email", "Phone number",
            "passwordHash", UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(
            v -> v.getMessage().contains("must be a well-formed email")));
  }

  // Test for phone number
  @Test
  @DisplayName("Should fail when phone number too long")
  void shouldFailWhenPhoneNumberTooLong() {
    User user = new User("First name", "Last name", "email@email.com",
            "12312312321213213213132", "passwordHash", UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath()
            .toString().equals("phoneNumber")));
  }

  // Test for password
  @Test
  @DisplayName("Should fail when password is blank")
  void shouldFailWhenPasswordIsBlank() {
    User user = new User("First name", "Last name", "email@email.com",
            "123123123212132132", "", UserRole.CUSTOMER, true);

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(
            v -> v.getMessage().contains("Password cannot be blank")));
  }

}
