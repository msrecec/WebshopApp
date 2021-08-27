package com.example.webshop.service.product;

import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.model.product.Product;
import com.example.webshop.model.product.ProductCommand;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductDTO> save(ProductCommand command);
    List<ProductDTO> findAll();
    Optional<ProductDTO> findByCode(String code);
    Optional<ProductDTO> update(ProductCommand command);
    void deleteByCode(String code);
}
