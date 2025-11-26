package com.example.e_commerce.model;

import com.example.e_commerce.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  private AddressType addressType;

  @NotBlank(message = "Street address cannot be blank")
  @Size(min = 3, max = 200,
          message = "Street address must be between 3 and 200 characters")
  private String streetAddress;

  @NotBlank(message = "City cannot be blank")
  @Size(min = 3, max = 200,
          message = "City must be between 3 and 200 characters")
  private String city;

  @NotBlank(message = "State cannot be blank")
  @Size(min = 3, max = 200,
          message = "State must be between 3 and 200 characters")
  private String state;

  @NotBlank(message = "PostalCode cannot be blank")
  @Size(min = 3, max = 10,
          message = "PostalCode must be between 3 and 10 characters")
  private String postalCode;

  @NotBlank(message = "Country cannot be blank")
  @Size(min = 3, max = 100,
          message = "Country must be between 3 and 100 characters")
  private String country;

  @Column(nullable = false, columnDefinition = "boolean default true")
  private boolean isDefault;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public Address() {
  }

  public Address(User user, AddressType addressType, String streetAddress,
                 String city, String state, String postalCode, String country,
                 boolean isDefault) {
    this.user = user;
    this.addressType = addressType;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
    this.country = country;
    this.isDefault = isDefault;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public AddressType getAddressType() {
    return addressType;
  }

  public void setAddressType(AddressType addressType) {
    this.addressType = addressType;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public boolean isDefault() {
    return isDefault;
  }

  public void setDefault(boolean aDefault) {
    isDefault = aDefault;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @PrePersist
  protected void onCreateAt() {
    this.createdAt = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "Address{" + "id=" + id + ", streetAddress=" + streetAddress + '\'' +
            ", city=" + city + '\'' + ", state=" + state + '\'' +
            ", postalCode=" + postalCode + '\'' + ", country=" + country +
            '\'' + ", isDefault=" + isDefault + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Address address)) return false;
    return Objects.equals(id, address.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
