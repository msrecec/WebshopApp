package com.example.webshop.service.order;

import com.example.webshop.command.order.OrderPostCommand;
import com.example.webshop.command.order.OrderPutCommand;
import com.example.webshop.command.order.nested.CustomerNestedInOrderCommand;
import com.example.webshop.command.order.nested.OrderItemNestedInOrderCommand;
import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.hnb.Currency;
import com.example.webshop.model.hnb.Hnb;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.order.Status;
import com.example.webshop.model.orderItem.OrderItem;
import com.example.webshop.model.product.Product;
import com.example.webshop.repository.customer.CustomerRepositoryJpa;
import com.example.webshop.repository.hnbAPI.HnbRepository;
import com.example.webshop.repository.order.OrderRepositoryJpa;
import com.example.webshop.repository.orderItem.OrderItemRepositoryJpa;
import com.example.webshop.repository.product.ProductRepositoryJpa;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderServiceImplTest {

    @MockBean(name = "orderItemRepositoryJpa")
    OrderItemRepositoryJpa orderItemRepositoryJpa;
    @MockBean(name = "productRepositoryJpa")
    ProductRepositoryJpa productRepositoryJpa;
    @MockBean(name = "customerRepositoryJpa")
    CustomerRepositoryJpa customerRepositoryJpa;
    @MockBean(name = "orderRepositoryJpa")
    OrderRepositoryJpa orderRepositoryJpa;
    @MockBean(name = "hnbRepository")
    HnbRepository hnbRepository;
    @MockBean(name = "session")
    Session session;
    @Autowired
    OrderService underTest;

    @Test
    void findAllTest() {

        // given

        List<Order> orderList = new ArrayList<>();
        Order order = Order.builder()
                .id(1L)
                .status(Status.DRAFT)
                .totalPriceHrk(new BigDecimal(100))
                .totalPriceEur(new BigDecimal(100))
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        Product product = Product.builder()
                .code("1234567891").description("test").isAvailable(true).name("test").priceHrk(new BigDecimal(100)).build();

        OrderItem orderItem = OrderItem.builder().quantity(1).id(1L).product(product).build();

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        orderList.add(order);

        when(orderRepositoryJpa.findAll()).thenReturn(orderList);

        // when

        List<OrderDTO> orderDTOList = underTest.findAll();

        // then

        assertThat(orderDTOList.size()).isEqualTo(1);
        assertThat(orderDTOList.get(0).getStatus().getStatus()).isEqualToIgnoringCase(Status.DRAFT.getStatus());
        assertThat(orderDTOList.get(0).getTotalPriceEur().intValue()).isEqualTo(100);

    }

    @Test
    void findByIdTest() {

        // given

        Order order = Order.builder()
                .id(1L)
                .status(Status.DRAFT)
                .totalPriceHrk(new BigDecimal(100))
                .totalPriceEur(new BigDecimal(100))
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        Product product = Product.builder()
                .code("1234567891").description("test").isAvailable(true).name("test").priceHrk(new BigDecimal(100)).build();

        OrderItem orderItem = OrderItem.builder().quantity(1).id(1L).product(product).build();

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        when(orderRepositoryJpa.findById(1L)).thenReturn(Optional.of(order));

        // when

        Optional<OrderDTO> orderDTO = underTest.findById(1L);

        // then

        assertThat(orderDTO.isPresent()).isTrue();
        assertThat(orderDTO.get().getStatus().getStatus()).isEqualToIgnoringCase(Status.DRAFT.getStatus());
        assertThat(orderDTO.get().getTotalPriceEur().intValue()).isEqualTo(100);

    }

    @Test
    void saveTest() {

        // given

        Order order = Order.builder()
                .id(1L)
                .status(Status.DRAFT)
                .totalPriceHrk(new BigDecimal(100))
                .totalPriceEur(new BigDecimal(100))
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        Product product = Product.builder()
                .code("1234567891").description("test").isAvailable(true).name("test").priceHrk(new BigDecimal(100)).build();

        OrderItem orderItem = OrderItem.builder().quantity(1).id(1L).product(product).build();

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        OrderItemNestedInOrderCommand item = OrderItemNestedInOrderCommand.builder().quantity(1).code("test").build();

        List<OrderItemNestedInOrderCommand> itemList = new ArrayList<>();

        itemList.add(item);

        CustomerNestedInOrderCommand customerNestedInOrderCommand = CustomerNestedInOrderCommand.builder()
                .email(customer.getEmail()).firstName(customer.getFirstName()).lastName(customer.getLastName()).build();

        OrderPostCommand command = OrderPostCommand.builder().customer(customerNestedInOrderCommand).orderItems(itemList).build();

        when(orderRepositoryJpa.save(any())).thenReturn(order);

        // when

        Optional<OrderDTO> orderDTO = underTest.save(command);

        // then

        assertThat(orderDTO.isPresent()).isTrue();
        assertThat(orderDTO.get().getStatus().getStatus()).isEqualToIgnoringCase(Status.DRAFT.getStatus());
        assertThat(orderDTO.get().getTotalPriceEur().intValue()).isEqualTo(100);
    }

    @Test
    void finalizeOrderTest() {

        // given

        Order order = Order.builder()
                .id(1L)
                .status(Status.DRAFT)
                .totalPriceHrk(new BigDecimal(100))
                .totalPriceEur(new BigDecimal(100))
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        Product product = Product.builder()
                .code("1234567891").description("test").isAvailable(true).name("test").priceHrk(new BigDecimal(100)).build();

        OrderItem orderItem = OrderItem.builder().quantity(1).id(1L).product(product).build();

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        when(orderRepositoryJpa.findById(1L)).thenReturn(Optional.of(order));
        when(session.merge(any())).thenReturn(new Object());
        when(hnbRepository.findByCurrency(Currency.EUR, "https://api.hnb.hr/tecajn/v1?valuta="))
                .thenReturn(Optional.of(Hnb.builder().srednjiZaDevize("7,484938").build()));

        // when

        Optional<OrderDTO> orderDTO = underTest.finalizeOrder(1L);

        // then

        assertThat(orderDTO.isPresent()).isTrue();
        assertThat(orderDTO.get().getStatus().getStatus()).isEqualToIgnoringCase(Status.SUBMITTED.getStatus());
        assertThat(orderDTO.get().getTotalPriceHrk().intValue()).isEqualTo(100);
        assertThat(orderDTO.get().getTotalPriceEur().equals(new BigDecimal(100).divide(new BigDecimal(7.484938), 2, RoundingMode.HALF_UP))).isTrue();
    }

    @Test
    void update() {

        // given

        Order order = Order.builder()
                .id(1L)
                .status(Status.DRAFT)
                .totalPriceHrk(new BigDecimal(100))
                .totalPriceEur(new BigDecimal(100))
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        Product product = Product.builder()
                .code("1234567891").description("test").isAvailable(true).name("test").priceHrk(new BigDecimal(100)).build();

        OrderItem orderItem = OrderItem.builder().quantity(1).id(1L).product(product).build();

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        OrderItemNestedInOrderCommand item = OrderItemNestedInOrderCommand.builder().quantity(1).code("test").build();

        List<OrderItemNestedInOrderCommand> itemList = new ArrayList<>();

        itemList.add(item);

        CustomerNestedInOrderCommand customerNestedInOrderCommand = CustomerNestedInOrderCommand.builder()
                .email(customer.getEmail()).firstName(customer.getFirstName()).lastName(customer.getLastName()).build();

        OrderPutCommand command = OrderPutCommand.builder().id(1L).status(Status.DRAFT).build();

        when(orderRepositoryJpa.findById(1L)).thenReturn(Optional.of(order));
        when(session.merge(any())).thenReturn(new Object());

        // when

        Optional<OrderDTO> orderDTO = underTest.update(command);

        // then

        assertThat(orderDTO.isPresent()).isTrue();
        assertThat(orderDTO.get().getStatus().getStatus().compareTo(Status.DRAFT.getStatus())==0).isTrue();

    }

    @Test
    void deleteById() {

        // given

        Order order = Order.builder()
                .id(1L)
                .status(Status.DRAFT)
                .totalPriceHrk(new BigDecimal(100))
                .totalPriceEur(new BigDecimal(100))
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        Product product = Product.builder()
                .code("1234567891").description("test").isAvailable(true).name("test").priceHrk(new BigDecimal(100)).build();

        OrderItem orderItem = OrderItem.builder().quantity(1).id(1L).product(product).build();

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        when(orderRepositoryJpa.findById(1L)).thenReturn(Optional.of(order));

        // when

        underTest.deleteById(1L);

        // then

        verify(session).remove(order.getCustomer());
        verify(session).remove(order.getOrderItems().get(0));
        verify(session).remove(order);

    }
}