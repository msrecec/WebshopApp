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
        return Product.builder()
                .code(command.getCode())
                .name(command.getName())
                .priceHrk(command.getPriceHrk())
                .description(command.getDescription())
                .isAvailable(command.getIsAvailable())
                .build();
    }
}
