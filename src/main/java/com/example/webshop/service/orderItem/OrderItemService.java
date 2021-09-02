package com.example.webshop.service.orderItem;

import com.example.webshop.command.orderItem.OrderItemPostCommand;
import com.example.webshop.command.orderItem.OrderItemPutCommand;
import com.example.webshop.dto.orderItem.OrderItemDTO;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItemDTO> findAll();
    Optional<OrderItemDTO> findById(Long id);
    Optional<OrderItemDTO> update(OrderItemPutCommand command);
    Optional<OrderItemDTO> save(OrderItemPostCommand command);
    void deleteById(Long id);
}
