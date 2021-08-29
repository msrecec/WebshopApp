package com.example.webshop.repository.product;

import com.example.webshop.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryJpa extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Optional<Product> findByCode(String code);
    Product save(Product product);
    @Query(value = "SELECT * FROM product INNER JOIN order_item ON product.id = order_item.product_id WHERE order_item.id = :id", nativeQuery = true)
    List<Product> findByOrderItem_Id(@Param("id")Long id);
}
