package com.example.webshop.service.order;

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

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderItemRepositoryJpa orderItemRepositoryJpa;
    private final ProductRepositoryJpa productRepositoryJpa;
    private final CustomerRepositoryJpa customerRepositoryJpa;
    private final OrderRepositoryJpa orderRepositoryJpa;

    public OrderServiceImpl(OrderItemRepositoryJpa orderItemRepositoryJpa,
                            ProductRepositoryJpa productRepositoryJpa,
                            CustomerRepositoryJpa customerRepositoryJpa,
                            OrderRepositoryJpa orderRepositoryJpa) {
        this.orderItemRepositoryJpa = orderItemRepositoryJpa;
        this.productRepositoryJpa = productRepositoryJpa;
        this.customerRepositoryJpa = customerRepositoryJpa;
        this.orderRepositoryJpa = orderRepositoryJpa;
    }


    @Override
    public Optional<Order> save(OrderCommand command) {

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemCommand orderItemCommand : command.getOrderItems()) {
            Optional<Product> product = productRepositoryJpa.findByCode(orderItemCommand.getCode());
            if(product.isPresent() && product.get().getIsAvailable()) {
                orderItems.add(orderItemRepositoryJpa.save(OrderItem.builder().quantity(orderItemCommand.getQuantity()).product(product.get()).build()));
            }
        }

        Customer customer = customerRepositoryJpa.save(Customer.builder()
                .firstName(command.getCustomer().getFirstName())
                .lastName(command.getCustomer().getLastName())
                .email(command.getCustomer().getEmail())
                .build());

        Order order = Order.builder().status(Status.DRAFT).customer(customer).orderItems(orderItems).build();

        return Optional.ofNullable(orderRepositoryJpa.save(order));

    }
}
