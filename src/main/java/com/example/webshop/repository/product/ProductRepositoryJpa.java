package com.example.webshop.repository.product;

import com.example.webshop.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryJpa extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Optional<Product> findByCode(String code);
    Product save(Product product);

}
