package com.example.e_commerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Email cannot be blank")
  @Email
  @Column(nullable = false, unique = true, length = 255)
  private String email;

  @NotBlank(message = "Password cannot be blank")
  @Column(nullable = false)
  private String passwordHash;

  @NotBlank(message = "First name cannot be blank")
  @Size(min = 3, max = 100,
          message = "First name must be between 3 and 100 characters")
  private String firstName;

  @NotBlank(message = "Last name cannot be blank")
  @Size(min = 3, max = 100,
          message = "Last name must be between 3 and 100 characters")
  private String lastName;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Size(max = 20, message = "Phone number must be less than 20 numbers")
  @Column(length = 20)
  private String phoneNumber;

  @OneToMany(mappedBy = "user")
  private List<Order> orders = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Address> addresses = new ArrayList<>();

  @OneToOne(mappedBy = "user")
  private Cart cart;

  @Column(nullable = false, columnDefinition = "boolean default true")
  private boolean active;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime lastLoginAt;

  public User() {
  }

  public User(String firstName, String lastName, String email,
              String phoneNumber, String passwordHash, UserRole role,
              boolean active) {
    this.email = email;
    this.passwordHash = passwordHash;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.active = active;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
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

  public LocalDateTime getLastLoginAt() {
    return lastLoginAt;
  }

  public void setLastLoginAt(LocalDateTime lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    lastLoginAt = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "User{id=" + id + ", First Name=" + firstName + '\'' +
            ", Last Name=" + lastName + '\'' + ", email=" + email + '\'' +
            ", phoneNumber=" + phoneNumber + '\'' + ", role=" + role +
            ", createdAt=" + createdAt + ", lastLoginAt=" + lastLoginAt +
            ", active=" + active + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User user)) return false;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
