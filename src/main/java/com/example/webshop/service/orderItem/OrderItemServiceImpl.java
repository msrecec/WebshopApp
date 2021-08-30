package com.example.webshop.service.orderItem;

import com.example.webshop.command.orderItem.OrderItemSingleCommand;
import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.mapping.mapper.orderItem.OrderItemMapper;
import com.example.webshop.mapping.mapper.orderItem.OrderItemMapperImpl;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.orderItem.OrderItem;
import com.example.webshop.repository.orderItem.OrderItemRepositoryJpa;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepositoryJpa orderItemRepositoryJpa;
    private final OrderItemMapper mapper;
    private final Session session;

    public OrderItemServiceImpl(OrderItemRepositoryJpa orderItemRepositoryJpa,
                                OrderItemMapperImpl mapper,
                                Session session) {
        this.orderItemRepositoryJpa = orderItemRepositoryJpa;
        this.mapper = mapper;
        this.session = session;
    }

    @Override
    public List<OrderItemDTO> findAll() {
        return orderItemRepositoryJpa.findAll().stream().map(mapper::mapOrderItemToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderItemDTO> findById(Long id) {
        return orderItemRepositoryJpa.findById(id).map(mapper::mapOrderItemToDTO);
    }

    @Override
    public Optional<OrderItemDTO> update(OrderItemSingleCommand command) {
        Optional<OrderItem> orderItem = orderItemRepositoryJpa.findById(command.getId());
        if(orderItem.isPresent()) {
            orderItem.get().setQuantity(command.getQuantity());
            return orderItem.map(mapper::mapOrderItemToDTO);
        } else {
            return Optional.empty();
        }
    }
}
