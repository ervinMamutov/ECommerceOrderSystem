package com.example.e_commerce.model;

import com.example.e_commerce.enums.AddressType;
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


public class AddressTest {
  private User user;
  private final String REPEATED_CHAR = "a";
  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    user = new User("First Name", "Last name", "email@email.com", "phoneNumber",
            "passwordHash", UserRole.CUSTOMER, true);
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Should create valid address")
  void shouldCreateValidAddress() {

    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city",
                    "state", "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);

    assertTrue(violations.isEmpty());
    assertEquals("streetAddress", address.getStreetAddress());
    assertEquals("city", address.getCity());
    assertEquals("state", address.getState());
    assertEquals("postalCode", address.getPostalCode());
    assertEquals("country", address.getCountry());
    assertTrue(address.isDefault());
  }

  // test for Street Address
  @Test
  @DisplayName("Should fail create valid address when streetAddress is blank")
  void shouldFailCreateValidAddressWhenStreetAddressIsBlank() {
    Address address =
            new Address(user, AddressType.SHIPPING, "", "city", "state",
                    "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(
            v -> v.getMessage().contains("Street address cannot be blank")));
  }

  @Test
  @DisplayName("Should fail create valid address when city <3")
  void shouldFailCreateValidAddressWhenStreetAddressLessThanThree() {
    Address address =
            new Address(user, AddressType.SHIPPING, "st", "city", "state",
                    "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("Street address must be between 3 and 200 characters")));
  }

  @Test
  @DisplayName("Should fail create valid address when streetDdress >200")
  void shouldFailCreateValidAddressWhenStreetAddressMoreThan200() {
    Address address = new Address(user, AddressType.SHIPPING,
            REPEATED_CHAR.repeat(201), "city", "state", "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("Street address must be between 3 and 200 characters")));
  }

  // test for City
  @Test
  @DisplayName("Should fail create valid address when city is blank")
  void shouldFailCreateValidAddressWhenCityIsBlank() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "",
                    "state", "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("City cannot be blank")));
  }

  @Test
  @DisplayName("Should fail create valid address when city <3")
  void shouldFailCreateValidAddressWhenCityLessThanThree() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "c",
                    "state", "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("City must be between 3 and 200 characters")));
  }

  @Test
  @DisplayName("Should fail create valid address when city >200")
  void shouldFailCreateValidAddressWhenCityMoreThan200() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress",
                    REPEATED_CHAR.repeat(205),
                    "state", "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("City must be between 3 and 200 characters")));
  }

  // test for State
  @Test
  @DisplayName("Should fail create valid address when state is blank")
  void shouldFailCreateValidAddressWhenStateIsBlank() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city", "",
                    "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("State cannot be blank")));
  }

  @Test
  @DisplayName("Should fail create valid address when state <3")
  void shouldFailCreateValidAddressWhenStateLessThanThree() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city",
                    "st", "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("State must be between 3 and 200 characters")));
  }

  @Test
  @DisplayName("Should fail create valid address when state more than 200")
  void shouldFailCreateValidAddressWhenStateMoreThan200() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city",
                    REPEATED_CHAR.repeat(201), "postalCode", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("State must be between 3 and 200 characters")));
  }

  // test for Postal Code
  @Test
  @DisplayName("Should fail create valid address when PostalCode is blank")
  void shouldFailCreateValidAddressWhenPostalCodeIsBlank() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city",
                    "state", "", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(
            v -> v.getMessage().contains("PostalCode cannot be blank")));
  }

  @Test
  @DisplayName("Should fail create valid address when PostalCode <3")
  void shouldFailCreateValidAddressWhenPostalCodeLessThanThree() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city",
                    "state", "po", "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("PostalCode must be between 3 and 10 characters")));
  }

  @Test
  @DisplayName("Should fail create valid address when PostalCode >10")
  void shouldFailCreateValidAddressWhenPostalCodeMoreThanTen() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city",
                    "state", REPEATED_CHAR.repeat(11), "country", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("PostalCode must be between 3 and 10 characters")));
  }

  // test for Country
  @Test
  @DisplayName("Should fail create valid address when country is blank")
  void shouldFailCreateValidAddressWhenCountryIsBlank() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city",
                    "state", "postalCode", "", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Country cannot be blank")));
  }

  @Test
  @DisplayName("Should fail create valid address when Country <3")
  void shouldFailCreateValidAddressWhenCountryLessThanThree() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city",
                    "state", "postalCode", "co", true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("Country must be between 3 and 100 characters")));
  }

  @Test
  @DisplayName("Should fail create valid address when Country > 100")
  void shouldFailCreateValidAddressWhenCountryMoreThan100() {
    Address address =
            new Address(user, AddressType.SHIPPING, "streetAddress", "city",
                    "state", "postalCode", REPEATED_CHAR.repeat(101), true);

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage()
            .contains("Country must be between 3 and 100 characters")));
  }
}
