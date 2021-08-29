package com.example.webshop.mapping.mapper.orderItem;

import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.mapping.mapper.product.ProductMapper;
import com.example.webshop.model.orderItem.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapperImpl implements OrderItemMapper{

    private final ProductMapper productMapper;

    @Autowired
    public OrderItemMapperImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public OrderItemDTO mapOrderItemToDTO(OrderItem orderItem) {
        return new OrderItemDTO(orderItem.getId(), productMapper.mapProductToDTO(orderItem.getProduct()), orderItem.getQuantity());
    }
}
