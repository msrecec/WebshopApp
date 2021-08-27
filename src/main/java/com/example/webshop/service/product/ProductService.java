package com.example.webshop.service.product;

import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.model.product.ProductCommand;

import java.util.Optional;

public interface ProductService {
    Optional<ProductDTO> save(ProductCommand command);
}
