package com.example.webshop.mapping.mapper.product;

import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.model.product.Product;
import com.example.webshop.model.product.ProductCommand;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO mapProductToDTO(Product product) {
        return new ProductDTO(
                product.getCode(),
                product.getName(),
                product.getPriceHrk(),
                product.getDescription(),
                product.getIsAvailable()
        );
    }

    @Override
    public Product mapCommandToProduct(ProductCommand command) {
        return new Product(
                null,
                command.getCode(),
                command.getName(),
                command.getPriceHrk(),
                command.getDescription(),
                command.getIsAvailable(),
                null
        );
    }
}
