package com.example.webshop.service.order;

import com.example.webshop.repository.customer.CustomerRepositoryJpa;
import com.example.webshop.repository.hnbAPI.HnbRepository;
import com.example.webshop.repository.order.OrderRepositoryJpa;
import com.example.webshop.repository.orderItem.OrderItemRepositoryJpa;
import com.example.webshop.repository.product.ProductRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderServiceImplTest {

    @MockBean
    OrderItemRepositoryJpa orderItemRepositoryJpa;
    @MockBean
    ProductRepositoryJpa productRepositoryJpa;
    @MockBean
    CustomerRepositoryJpa customerRepositoryJpa;
    @MockBean
    OrderRepositoryJpa orderRepositoryJpa;
    @MockBean
    HnbRepository hnbRepository;
    @Autowired
    OrderService orderService;

    @Test
    void findAll() {

        // given



        // when

        // then

    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void finalizeOrder() {
    }

    @Test
    void getHnbApi() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}