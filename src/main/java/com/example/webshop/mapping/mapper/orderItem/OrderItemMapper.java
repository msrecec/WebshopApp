package com.example.webshop.mapping.mapper.orderItem;

import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.orderItem.OrderItem;

public interface OrderItemMapper {
    OrderItemDTO mapOrderItemToDTO(OrderItem orderItem);
}
