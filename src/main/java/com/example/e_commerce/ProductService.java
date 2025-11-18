package com.example.e_commerce;

import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
  private ProductRepository productRepository;

  public ProductService() {}

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional
  public Order purchaseProduct(@Valid Long productId, Long userId,
                               int quantity) {
    Product product = productRepository.findByIdWithLock(productId)
            .orElseThrow();
  }
}
