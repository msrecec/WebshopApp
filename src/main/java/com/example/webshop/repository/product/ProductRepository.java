package com.example.webshop.repository.product;

import com.example.webshop.model.product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> update(Product product);
    void delete(String code);
}
