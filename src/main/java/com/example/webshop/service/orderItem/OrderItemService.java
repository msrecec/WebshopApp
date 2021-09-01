package com.example.webshop.service.orderItem;

import com.example.webshop.command.orderItem.single.OrderItemSingleSaveCommand;
import com.example.webshop.command.orderItem.single.OrderItemSingleUpdateCommand;
import com.example.webshop.dto.orderItem.OrderItemDTO;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItemDTO> findAll();
    Optional<OrderItemDTO> findById(Long id);
    Optional<OrderItemDTO> update(OrderItemSingleUpdateCommand command);
    Optional<OrderItemDTO> save(OrderItemSingleSaveCommand command);
    void deleteById(Long id);
}
