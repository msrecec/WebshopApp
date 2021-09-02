package com.example.webshop.service.orderItem;

import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.order.Status;
import com.example.webshop.model.orderItem.OrderItem;
import com.example.webshop.model.product.Product;
import com.example.webshop.repository.order.OrderRepositoryJpa;
import com.example.webshop.repository.orderItem.OrderItemRepositoryJpa;
import com.example.webshop.repository.product.ProductRepositoryJpa;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderItemServiceImplTest {
    @MockBean(name = "orderItemRepositoryJpa")
    OrderItemRepositoryJpa orderItemRepositoryJpa;
    @MockBean(name = "orderRepositoryJpa")
    OrderRepositoryJpa orderRepositoryJpa;
    @MockBean(name = "productRepositoryJpa")
    ProductRepositoryJpa productRepositoryJpa;
    @MockBean(name = "session")
    Session session;

    @Autowired
    OrderItemService underTest;

    @Test
    void findAll() {

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
                .code("1234567891").description("test").isAvailable(true)
                .name("test").priceHrk(new BigDecimal(100)).build();

        OrderItem orderItem = OrderItem.builder().quantity(1).id(1L).product(product).build();

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        orderItem.setOrder(order);

        when(orderItemRepositoryJpa.findAll()).thenReturn(orderItemList);

        // when

        List<OrderItemDTO> orderItemDTOList = underTest.findAll();

        // then

        assertThat(orderItemDTOList.size()).isEqualTo(1);
        assertThat(orderItemDTOList.get(0).getQuantity()).isEqualTo(1);
        assertThat(orderItemDTOList.get(0).getId()).isEqualTo(1);
        assertThat(orderItemDTOList.get(0).getProduct().getName()).isEqualToIgnoringCase("test");

    }

    @Test
    void findById() {

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
                .code("1234567891").description("test").isAvailable(true)
                .name("test").priceHrk(new BigDecimal(100)).build();

        OrderItem orderItem = OrderItem.builder().quantity(1).id(1L).product(product).build();

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        orderItem.setOrder(order);

        when(orderItemRepositoryJpa.findById(1L)).thenReturn(Optional.of(orderItem));

        // when

        Optional<OrderItemDTO> orderItemDTO = underTest.findById(1L);

        // then

        assertThat(orderItemDTO.isPresent()).isTrue();
        assertThat(orderItemDTO.get().getQuantity()).isEqualTo(1);
        assertThat(orderItemDTO.get().getId()).isEqualTo(1);
        assertThat(orderItemDTO.get().getProduct().getName()).isEqualToIgnoringCase("test");
    }

    @Test
    void update() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }
}