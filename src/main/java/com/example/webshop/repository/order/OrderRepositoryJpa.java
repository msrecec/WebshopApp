package com.example.webshop.repository.order;

import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryJpa extends JpaRepository<Order, Long> {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
}
