package com.example.webshop.repository.orderItem;

import com.example.webshop.model.order.Order;
import com.example.webshop.model.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepositoryJpa extends JpaRepository<OrderItem, Long> {
    OrderItem save(OrderItem orderItem);
    Optional<OrderItem> findById(Long id);
}
