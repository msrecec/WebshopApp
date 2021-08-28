package com.example.webshop.repository.order;

import com.example.webshop.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoryJpa extends JpaRepository<Order, Long> {
    Order save(Order order);
}
