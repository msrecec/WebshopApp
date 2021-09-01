package com.example.webshop.service.orderItem;

import com.example.webshop.command.orderItem.OrderItemSaveCommand;
import com.example.webshop.command.orderItem.OrderItemUpdateCommand;
import com.example.webshop.dto.orderItem.OrderItemDTO;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItemDTO> findAll();
    Optional<OrderItemDTO> findById(Long id);
    Optional<OrderItemDTO> update(OrderItemUpdateCommand command);
    Optional<OrderItemDTO> save(OrderItemSaveCommand command);
    void deleteById(Long id);
}
