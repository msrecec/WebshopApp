package com.example.webshop.service.order;

import com.example.webshop.command.order.OrderSaveCommand;
import com.example.webshop.command.order.OrderUpdateCommand;
import com.example.webshop.command.orderItem.OrderItemSaveCommand;
import com.example.webshop.command.orderItem.OrderItemUpdateCommand;
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
                            HnbRepositoryImpl hnbRepository,
                            OrderMapperImpl orderMapper,
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
    public Optional<OrderDTO> save(OrderSaveCommand command) {

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

        for(OrderItemSaveCommand orderItemSaveCommand : command.getOrderItems()) {
            Optional<Product> product = productRepositoryJpa.findByCode(orderItemSaveCommand.getCode());
            if(product.isPresent() && product.get().getIsAvailable()) {
                OrderItem orderItem  = OrderItem.builder()
                        .quantity(orderItemSaveCommand.getQuantity())
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
            BigDecimal totalPriceEur = null;

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

            Optional<Hnb> hnb = getHnbApi();

            if(hnb.isPresent()) {
                totalPriceEur = totalPriceHrk.multiply(new BigDecimal(hnb.get().getSrednjiZaDevize().replace(",", ".")));
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

    public Optional<Hnb> getHnbApi() {
        return hnbRepository.findByCurrency(Currency.EUR);
    }

    @Transactional
    public Optional<OrderDTO> update(OrderUpdateCommand command) {

        Optional<Customer> customerOptional = Optional.ofNullable(customerRepositoryJpa.findByWebshopOrder_Id(command.getId()).get(0));

        if(customerOptional.isPresent()) {
            customerOptional.get().setFirstName(command.getCustomer().getFirstName());
            customerOptional.get().setLastName(command.getCustomer().getLastName());
            customerOptional.get().setEmail(command.getCustomer().getEmail());
            session.merge(customerOptional.get());
        }

        command.getOrderItems().forEach(orderItem -> {
            Optional<OrderItem> orderItemOptional = orderItemRepositoryJpa.findById(orderItem.getId());
            if(orderItemOptional.isPresent()) {
                Optional<Product> productOptional = Optional.ofNullable(productRepositoryJpa.findByOrderItem_Id(orderItem.getId()).get(0));
                if(productOptional.isPresent() && productOptional.get().getIsAvailable()) {
                    orderItemOptional.get().setQuantity(orderItem.getQuantity());
                    session.merge(orderItemOptional.get());
                }
            }
        });

        Optional<Order> orderOptional = orderRepositoryJpa.findById(command.getId());

        if(orderOptional.isPresent()) {
            if(command.getStatus().compareTo(Status.SUBMITTED) == 0) {
                BigDecimal totalPriceHrk = new BigDecimal(0);
                for(OrderItemUpdateCommand orderItem : command.getOrderItems()) {
                    Optional<Product> productOptional = productRepositoryJpa.findByCode(orderItem.getCode());
                    if (productOptional.isPresent() && productOptional.get().getIsAvailable()) {
                        totalPriceHrk = totalPriceHrk.add(productOptional.get().getPriceHrk().multiply(new BigDecimal(orderItem.getQuantity())));
                    }
                }
                BigDecimal totalPriceEur = null;

                Optional<Hnb> hnb = getHnbApi();

                if(hnb.isPresent()) {
                    totalPriceEur = totalPriceHrk.multiply(new BigDecimal(hnb.get().getSrednjiZaDevize().replace(",", ".")));
                }

                orderOptional.get().setTotalPriceHrk(totalPriceHrk);
                orderOptional.get().setTotalPriceEur(totalPriceEur);
                orderOptional.get().setStatus(Status.SUBMITTED);
            } else {
                orderOptional.get().setTotalPriceHrk(null);
                orderOptional.get().setTotalPriceEur(null);
                orderOptional.get().setStatus(Status.DRAFT);
            }
            session.merge(orderOptional.get());
        }

        return orderOptional.map(orderMapper::mapOrderToDTO);
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
