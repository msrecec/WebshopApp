package com.example.webshop.mapping.mapper.order;

import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.mapping.mapper.customer.CustomerMapper;
import com.example.webshop.mapping.mapper.orderItem.OrderItemMapper;
import com.example.webshop.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements OrderMapper {

    private final CustomerMapper customerMapper;
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderMapperImpl(CustomerMapper customerMapper, OrderItemMapper orderItemMapper) {
        this.customerMapper = customerMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderDTO mapOrderToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getTotalPriceHrk(),
                order.getTotalPriceEur(),
                customerMapper.mapCustomerToDTO(order.getCustomer()),
                order.getOrderItems().stream().map(orderItemMapper::mapOrderItemToDTO).collect(Collectors.toList())
        );
    }
}
