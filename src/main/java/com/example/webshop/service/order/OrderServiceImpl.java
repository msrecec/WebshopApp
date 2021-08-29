package com.example.webshop.service.order;

import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.mapping.mapper.order.OrderMapper;
import com.example.webshop.mapping.mapper.order.OrderMapperImpl;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.order.OrderCommand;
import com.example.webshop.model.order.Status;
import com.example.webshop.model.orderItem.OrderItem;
import com.example.webshop.model.orderItem.OrderItemCommand;
import com.example.webshop.model.product.Product;
import com.example.webshop.repository.customer.CustomerRepositoryJpa;
import com.example.webshop.repository.order.OrderRepositoryJpa;
import com.example.webshop.repository.orderItem.OrderItemRepositoryJpa;
import com.example.webshop.repository.product.ProductRepositoryJpa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderItemRepositoryJpa orderItemRepositoryJpa;
    private final ProductRepositoryJpa productRepositoryJpa;
    private final CustomerRepositoryJpa customerRepositoryJpa;
    private final OrderRepositoryJpa orderRepositoryJpa;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderItemRepositoryJpa orderItemRepositoryJpa,
                            ProductRepositoryJpa productRepositoryJpa,
                            CustomerRepositoryJpa customerRepositoryJpa,
                            OrderRepositoryJpa orderRepositoryJpa,
                            OrderMapperImpl orderMapper) {
        this.orderItemRepositoryJpa = orderItemRepositoryJpa;
        this.productRepositoryJpa = productRepositoryJpa;
        this.customerRepositoryJpa = customerRepositoryJpa;
        this.orderRepositoryJpa = orderRepositoryJpa;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepositoryJpa.findAll().stream().map(orderMapper::mapOrderToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDTO> findById(Long id) {
        return orderRepositoryJpa.findById(id).map(orderMapper::mapOrderToDTO);
    }

    @Override
    public Optional<OrderDTO> save(OrderCommand command) {

        List<OrderItem> orderItems = new ArrayList<>();

        Customer customer = customerRepositoryJpa.save(Customer.builder()
                .firstName(command.getCustomer().getFirstName())
                .lastName(command.getCustomer().getLastName())
                .email(command.getCustomer().getEmail())
                .build());

        Optional<Order> order = Optional.ofNullable(
                orderRepositoryJpa.save(Order.builder()
                .status(Status.DRAFT)
                .customer(customer)
                .build()));


        /**
         * Saves orderItem to DB if product is available
         */

        for(OrderItemCommand orderItemCommand : command.getOrderItems()) {

            Optional<Product> product = productRepositoryJpa.findByCode(orderItemCommand.getCode());

            if(product.isPresent() && product.get().getIsAvailable()) {

                OrderItem orderItem  = OrderItem.builder()
                        .quantity(orderItemCommand.getQuantity())
                        .product(product.get())
                        .build();

                if(order.isPresent()) {
                    orderItem.setOrder(order.get());
                }

                orderItems.add(orderItemRepositoryJpa.save(orderItem));
            }
        }

        if(order.isPresent()) {
            order.get().setOrderItems(orderItems);
        }

        return order.map(orderMapper::mapOrderToDTO);

    }
}
