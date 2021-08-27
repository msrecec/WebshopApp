package com.example.webshop.repository.product;

import com.example.webshop.model.product.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> update(Product product);
    void delete(String code);
}
