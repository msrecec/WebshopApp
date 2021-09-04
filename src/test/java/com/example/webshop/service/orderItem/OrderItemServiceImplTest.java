package com.example.webshop.service.orderItem;

import com.example.webshop.command.orderItem.OrderItemPostCommand;
import com.example.webshop.command.orderItem.OrderItemPutCommand;
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
import org.junit.jupiter.api.BeforeEach;
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

    Order order;

    Customer customer;

    Product product;

    OrderItem orderItem;

    @BeforeEach
    void setUp() {

        order = Order.builder()
                .id(1L)
                .status(Status.DRAFT)
                .totalPriceHrk(new BigDecimal(100))
                .totalPriceEur(new BigDecimal(100))
                .build();

        customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        product = Product.builder()
                .code("1234567891").description("test").isAvailable(true)
                .name("test").priceHrk(new BigDecimal(100)).build();

        orderItem = OrderItem.builder().quantity(1).id(1L).product(product).build();

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        orderItem.setOrder(order);
    }

    @Test
    void findAllTest() {

        // given

        List<OrderItem> orderItems = new ArrayList<>();

        orderItems.add(orderItem);

        order.setCustomer(customer);
        order.setOrderItems(orderItems);

        orderItem.setOrder(order);

        when(orderItemRepositoryJpa.findAll()).thenReturn(orderItems);

        // when

        List<OrderItemDTO> orderItemDTOList = underTest.findAll();

        // then

        assertThat(orderItemDTOList.size()).isEqualTo(1);
        assertThat(orderItemDTOList.get(0).getQuantity()).isEqualTo(1);
        assertThat(orderItemDTOList.get(0).getId()).isEqualTo(1);
        assertThat(orderItemDTOList.get(0).getProduct().getName()).isEqualToIgnoringCase("test");

    }

    @Test
    void findByIdTest() {

        // given

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
    void updateTest() {

        // given

        OrderItemPutCommand command = OrderItemPutCommand.builder().id(1L).code("test").quantity(1).build();

        when(orderItemRepositoryJpa.findById(1L)).thenReturn(Optional.of(orderItem));

        // when

        Optional<OrderItemDTO> orderItemDTO = underTest.update(command);

        // then

        assertThat(orderItemDTO.isPresent()).isTrue();
        assertThat(orderItemDTO.get().getId()).isEqualTo(1L);
        assertThat(orderItemDTO.get().getQuantity()).isEqualTo(1);
        assertThat(orderItemDTO.get().getProduct().getName()).isEqualTo("test");

    }

    @Test
    void saveTest() {

        // given

        OrderItemPostCommand command = OrderItemPostCommand.builder().orderId(1L).code("test").quantity(1).build();
        when(orderRepositoryJpa.findById(1L)).thenReturn(Optional.of(order));
        when(productRepositoryJpa.findByCode("test")).thenReturn(Optional.of(product));
        when(orderItemRepositoryJpa.save(any())).thenReturn(orderItem);

        // when

        Optional<OrderItemDTO> orderItemDTO = underTest.save(command);

        // then

        assertThat(orderItemDTO.isPresent()).isTrue();
        assertThat(orderItemDTO.get().getId()).isEqualTo(1L);
        assertThat(orderItemDTO.get().getProduct().getName()).isEqualToIgnoringCase("test");

    }

    @Test
    void deleteByIdTest() {

        // given

        when(orderItemRepositoryJpa.findById(1L)).thenReturn(Optional.of(orderItem));

        // when

        underTest.deleteById(1L);

        // then

        verify(session).remove(orderItem);

    }
}