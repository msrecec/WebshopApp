package com.example.webshop.repository.orderItem;


import com.example.webshop.model.product.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private JdbcTemplate jdbc;

    public OrderItemRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    @Transactional
    public void delete(Long id) {
        try {
            jdbc.update("DELETE FROM order_item WHERE id = ?", id);
        } catch (DataAccessException ignored) {
        }
    }
}
