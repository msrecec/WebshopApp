package com.example.webshop.service.orderItem;

import com.example.webshop.command.orderItem.single.OrderItemSingleSaveCommand;
import com.example.webshop.command.orderItem.single.OrderItemSingleUpdateCommand;
import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.mapping.mapper.orderItem.OrderItemMapper;
import com.example.webshop.mapping.mapper.orderItem.OrderItemMapperImpl;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.orderItem.OrderItem;
import com.example.webshop.model.product.Product;
import com.example.webshop.repository.order.OrderRepositoryJpa;
import com.example.webshop.repository.orderItem.OrderItemRepositoryJpa;
import com.example.webshop.repository.product.ProductRepositoryJpa;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepositoryJpa orderItemRepositoryJpa;
    private final OrderRepositoryJpa orderRepositoryJpa;
    private final ProductRepositoryJpa productRepositoryJpa;
    private final OrderItemMapper mapper;
    private final Session session;

    public OrderItemServiceImpl(OrderItemRepositoryJpa orderItemRepositoryJpa,
                                OrderItemMapperImpl mapper,
                                OrderRepositoryJpa orderRepositoryJpa,
                                ProductRepositoryJpa productRepositoryJpa,
                                Session session) {
        this.orderItemRepositoryJpa = orderItemRepositoryJpa;
        this.orderRepositoryJpa = orderRepositoryJpa;
        this.productRepositoryJpa = productRepositoryJpa;
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
    public Optional<OrderItemDTO> update(OrderItemSingleUpdateCommand command) {
        Optional<OrderItem> orderItem = orderItemRepositoryJpa.findById(command.getId());
        if(orderItem.isPresent()) {
            orderItem.get().setQuantity(command.getQuantity());
            return orderItem.map(mapper::mapOrderItemToDTO);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Saves orderItem to db only if related product and order are present
     *
     * @param command
     * @return
     */

    @Override
    public Optional<OrderItemDTO> save(OrderItemSingleSaveCommand command) {
        Optional<Order> order = orderRepositoryJpa.findById(command.getOrderId());
        Optional<Product> product = productRepositoryJpa.findByCode(command.getCode());
        if(order.isPresent() && product.isPresent()) {
            Optional<OrderItem> orderItem = Optional.of(new OrderItem());
            orderItem.get().setOrder(order.get());
            orderItem.get().setProduct(product.get());
            orderItem.get().setQuantity(command.getQuantity());

            orderItem = Optional.of(orderItemRepositoryJpa.save(orderItem.get()));

            return orderItem.map(mapper::mapOrderItemToDTO);

        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<OrderItem> orderItem = orderItemRepositoryJpa.findById(id);
        if(orderItem.isPresent()) {
            session.remove(orderItem.get());
        }
    }
}
