package com.example.webshop.repository.product;
import static org.assertj.core.api.Assertions.*;

import com.example.webshop.model.orderItem.OrderItem;
import com.example.webshop.model.product.Product;
import com.example.webshop.repository.orderItem.OrderItemRepositoryImpl;
import com.example.webshop.repository.orderItem.OrderItemRepositoryJpa;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
class ProductRepositoryImplTest {

    @Autowired
    ProductRepositoryImpl underTest;

    @Autowired
    ProductRepositoryJpa productRepositoryJpa;

    @Autowired
    OrderItemRepositoryJpa orderItemRepositoryJpa;

    @Autowired
    OrderItemRepositoryImpl orderItemRepositoryImpl;

    @Autowired
    Session session;

    @Test
    @Transactional
    @DirtiesContext
    void updateExistsTest() {

        // given

        Product product = Product.builder()
                .code("1234567890")
                .name("Test")
                .description("Test")
                .isAvailable(true)
                .priceHrk(new BigDecimal(100))
                .build();

        // when

        Optional<Product> optionalProduct = underTest.update(product);

        // then

        assertThat(optionalProduct.isPresent()).isTrue();
        assertThat(optionalProduct.get().getIsAvailable()).isTrue();
        assertThat(optionalProduct.get().getName()).isEqualToIgnoringCase("test");
        assertThat(optionalProduct.get().getPriceHrk().intValue()).isEqualTo(100);

    }

    @Test
    @Transactional
    @DirtiesContext
    void updateNotExistsTest() {
        // given

        Product product = Product.builder()
                .code("1234567895")
                .name("Test")
                .description("Test")
                .isAvailable(true)
                .priceHrk(new BigDecimal(100))
                .build();

        // when

        Optional<Product> optionalProduct = underTest.update(product);

        // then

        assertThat(optionalProduct.isEmpty());

    }

    @Test
    @Transactional
    @DirtiesContext
    void delete() {

        // given

        String code = "1234567890";

        List<OrderItem> orderItemList = orderItemRepositoryJpa.findAll();

        orderItemList.forEach(orderItem -> {
            orderItemRepositoryImpl.delete(orderItem.getId());
        });

        // when

        underTest.delete(code);

        // then

        Optional<Product> optionalProduct = productRepositoryJpa.findByCode(code);

        assertThat(optionalProduct.isEmpty()).isTrue();

    }
}