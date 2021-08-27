package com.example.webshop.repository.product;

import com.example.webshop.model.product.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository{

    private JdbcTemplate jdbc;
    private ProductRepositoryJpa productRepositoryJpa;

    public ProductRepositoryImpl(JdbcTemplate jdbc, ProductRepositoryJpa productRepositoryJpa) {
        this.jdbc = jdbc;
        this.productRepositoryJpa = productRepositoryJpa;
    }

    @Override
    public Optional<Product> update(Product product) {
        int executed = jdbc.update("UPDATE product SET " +
                "code = ?, " +
                "name = ?, " +
                "price_hrk = ?, " +
                "description = ?, " +
                "is_available = ? " +
                "WHERE code = ?",
                product.getCode(),
                product.getName(),
                product.getPriceHrk(),
                product.getDescription(),
                product.getIsAvailable(),
                product.getCode());

        if(executed > 0) {
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void delete(String code) {
        Optional<Product> product = productRepositoryJpa.findByCode(code);
        product.ifPresent(value -> jdbc.update("DELETE FROM product WHERE code = ?", value.getCode()));
    }
}
