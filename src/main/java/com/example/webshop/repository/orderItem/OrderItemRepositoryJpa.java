package com.example.webshop.repository.orderItem;

import com.example.webshop.model.order.Order;
import com.example.webshop.model.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepositoryJpa extends JpaRepository<Order, Long> {
    OrderItem save(OrderItem orderItem);
}
