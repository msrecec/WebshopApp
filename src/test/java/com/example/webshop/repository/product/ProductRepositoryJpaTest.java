package com.example.webshop.repository.product;

import com.example.webshop.model.product.Product;
import com.example.webshop.repository.orderItem.OrderItemRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductRepositoryJpaTest {

    @Autowired
    ProductRepositoryJpa productRepositoryJpa;

    @Test
    void findByOrderItem_IdTest() {

        // given

        Long orderItemId = 1L;

        // when

        List<Product> orderItemList = productRepositoryJpa.findByOrderItem_Id(orderItemId);

        // then

        assertThat(orderItemList.size()).isEqualTo(1);
        assertThat(orderItemList.get(0).getPriceHrk().compareTo(new BigDecimal(1000))==0).isTrue();
        assertThat(orderItemList.get(0).getName()).isEqualToIgnoringCase("Great product");

    }
}