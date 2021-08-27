package com.example.webshop.service.product;

import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.mapping.mapper.product.ProductMapper;
import com.example.webshop.model.product.Product;
import com.example.webshop.model.product.ProductCommand;
import com.example.webshop.repository.product.ProductRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private ProductMapper mapper;
    private ProductRepositoryJpa productRepositoryJpa;

    @Autowired
    public ProductServiceImpl(ProductMapper mapper, ProductRepositoryJpa productRepositoryJpa) {
        this.mapper = mapper;
        this.productRepositoryJpa = productRepositoryJpa;
    }

    @Override
    public Optional<ProductDTO> save(ProductCommand command) {
        return Optional.ofNullable(
                productRepositoryJpa.save(mapper.mapCommandToProduct(command))
        ).map(mapper::mapProductToDTO);
    }
}
