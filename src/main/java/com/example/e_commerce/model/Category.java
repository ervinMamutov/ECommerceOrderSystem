package com.example.e_commerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name cannot be blank")
  @Size(min = 3, max = 100,
          message = "Category name must be between 3 and 100 characters")
  @Column(unique = true)
  private String name;

  @NotBlank(message = "Description cannot be blank")
  @Size(max = 500, message = "Description must be max 500 characters")
  private String description;

  @ManyToOne
  @JoinColumn(nullable = true)
  private Category parentCategory;

  @OneToMany(mappedBy = "category")
  private List<Product> products = new ArrayList<>();

  @Column(nullable = false, columnDefinition = "boolean default true")
  private boolean active;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public Category() {
  }

  public Category(String name, String description, Category parentCategory,
                  boolean active) {
    this.name = name;
    this.description = description;
    this.parentCategory = parentCategory;
    this.active = active;
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

  public Category getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(Category parentCategory) {
    this.parentCategory = parentCategory;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
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

  @PrePersist
  protected void onUpdate() {
    createdAt = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "Category{" + "id=" + id +
            ", name=" + name + '\'' +
            ", description=" + description + '\'' +
            ", parentsCategoryId=" +
            (parentCategory != null ? parentCategory.getId() : null) +
            ", products=" + products +
            ", active=" + active +
            "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Category category)) return false;
    return Objects.equals(id, category.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
