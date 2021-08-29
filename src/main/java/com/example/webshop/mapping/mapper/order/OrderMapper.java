package com.example.webshop.mapping.mapper.order;

import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.model.order.Order;

public interface OrderMapper {
    OrderDTO mapOrderToDTO(Order order);
}
