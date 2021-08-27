package com.example.webshop.mapping.mapper.product;

import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.model.product.Product;
import com.example.webshop.model.product.ProductCommand;

public interface ProductMapper {
    ProductDTO mapProductToDTO(Product product);
    Product mapCommandToProduct(ProductCommand command);
}
