package com.example.webshop.service.order;

import com.example.webshop.command.order.OrderPostCommand;
import com.example.webshop.command.order.OrderPutCommand;
import com.example.webshop.command.order.nested.OrderItemNestedInOrderCommand;
import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.mapping.mapper.order.OrderMapper;
import com.example.webshop.mapping.mapper.order.OrderMapperImpl;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.hnb.Currency;
import com.example.webshop.model.hnb.Hnb;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.order.Status;
import com.example.webshop.model.orderItem.OrderItem;
import com.example.webshop.model.product.Product;
import com.example.webshop.repository.customer.CustomerRepositoryJpa;
import com.example.webshop.repository.hnbAPI.HnbRepository;
import com.example.webshop.repository.hnbAPI.HnbRepositoryImpl;
import com.example.webshop.repository.order.OrderRepositoryJpa;
import com.example.webshop.repository.orderItem.OrderItemRepositoryJpa;
import com.example.webshop.repository.product.ProductRepositoryJpa;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderItemRepositoryJpa orderItemRepositoryJpa;
    private final ProductRepositoryJpa productRepositoryJpa;
    private final CustomerRepositoryJpa customerRepositoryJpa;
    private final OrderRepositoryJpa orderRepositoryJpa;
    private final HnbRepository hnbRepository;
    private final OrderMapper orderMapper;
    private final Session session;

    @Autowired
    public OrderServiceImpl(OrderItemRepositoryJpa orderItemRepositoryJpa,
                            ProductRepositoryJpa productRepositoryJpa,
                            CustomerRepositoryJpa customerRepositoryJpa,
                            OrderRepositoryJpa orderRepositoryJpa,
                            HnbRepository hnbRepository,
                            OrderMapper orderMapper,
                            Session session) {
        this.orderItemRepositoryJpa = orderItemRepositoryJpa;
        this.productRepositoryJpa = productRepositoryJpa;
        this.customerRepositoryJpa = customerRepositoryJpa;
        this.orderRepositoryJpa = orderRepositoryJpa;
        this.hnbRepository = hnbRepository;
        this.orderMapper = orderMapper;
        this.session = session;
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
    @Transactional
    public Optional<OrderDTO> save(OrderPostCommand command) {

        List<OrderItem> orderItems = new ArrayList<>();

        /**
         * Saves customer based on customer data
         *
         */

        Customer customer = customerRepositoryJpa.save(Customer.builder()
                .firstName(command.getCustomer().getFirstName())
                .lastName(command.getCustomer().getLastName())
                .email(command.getCustomer().getEmail())
                .build());

        /**
         * Saves order as DRAFT based on customer object
         *
         */

        Optional<Order> order = Optional.ofNullable(
                orderRepositoryJpa.save(Order.builder()
                .status(Status.DRAFT)
                .customer(customer)
                .build()));

        /**
         * Saves orderItem based on product to DB if product is available
         *
         */

        for(OrderItemNestedInOrderCommand orderItemNestedInOrderCommand : command.getOrderItems()) {
            Optional<Product> product = productRepositoryJpa.findByCode(orderItemNestedInOrderCommand.getCode());
            if(product.isPresent()) {
                OrderItem orderItem  = OrderItem.builder()
                        .quantity(orderItemNestedInOrderCommand.getQuantity())
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


    @Transactional
    public Optional<OrderDTO> finalizeOrder(Long id) {
        Optional<Order> orderOptional = orderRepositoryJpa.findById(id);
        boolean productFoundFlag = false;

        if(orderOptional.isPresent()) {
            List<OrderItem> orderItems = orderOptional.get().getOrderItems();
            BigDecimal totalPriceHrk = new BigDecimal(0);
            BigDecimal totalPriceEur;

            /**
             * Aggregates total price by orderItems quantity and product value
             *
             */

            for(OrderItem item : orderItems) {
                Product product = item.getProduct();

                if(product.getIsAvailable()) {
                    totalPriceHrk = totalPriceHrk.add(new BigDecimal(item.getQuantity()).multiply(product.getPriceHrk()));
                    productFoundFlag = true;
                }

            }

            if(!productFoundFlag) {
                return Optional.empty();
            }

            /**
             * Gets Euro currency value in KN
             *
             */

            Optional<Hnb> hnb = hnbRepository.findByCurrency(Currency.EUR);

            if(hnb.isPresent()) {
                totalPriceEur = totalPriceHrk.divide(new BigDecimal(hnb.get().getSrednjiZaDevize().replace(",", ".")), 2, RoundingMode.HALF_UP);
            } else {
                return Optional.empty();
            }

            orderOptional.get().setStatus(Status.SUBMITTED);
            orderOptional.get().setTotalPriceHrk(totalPriceHrk);
            orderOptional.get().setTotalPriceEur(totalPriceEur);

            session.merge(orderOptional.get());

            return orderOptional.map(orderMapper::mapOrderToDTO);

        } else {
            return Optional.empty();
        }
    }

    public Optional<Hnb> getHnb(Currency currency) {
        return hnbRepository.findByCurrency(currency);
    }

    @Transactional
    public Optional<OrderDTO> update(OrderPutCommand command) {
        Optional<Order> order = orderRepositoryJpa.findById(command.getId());
        if(order.isPresent()) {
            if(command.getStatus().compareTo(Status.SUBMITTED) == 0) {
                return finalizeOrder(command.getId());
            } else if(command.getStatus().compareTo(Status.DRAFT) == 0) {
                order.get().setStatus(Status.DRAFT);
                order.get().setTotalPriceEur(null);
                order.get().setTotalPriceHrk(null);
                session.merge(order.get());
                return order.map(orderMapper::mapOrderToDTO);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Order> order = orderRepositoryJpa.findById(id);
        if(order.isPresent()) {
            Customer customer = order.get().getCustomer();
            List<OrderItem> orderItems = order.get().getOrderItems();
            if(customer != null) {
                session.remove(customer);
            }
            for(OrderItem item : orderItems) {
                session.remove(item);
            }

            session.remove(order.get());
        }
    }
}
