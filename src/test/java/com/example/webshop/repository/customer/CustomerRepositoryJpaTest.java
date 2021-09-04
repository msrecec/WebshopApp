package com.example.webshop.repository.customer;

import com.example.webshop.model.customer.Customer;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
class CustomerRepositoryJpaTest {

    @Autowired
    CustomerRepositoryJpa underTest;

    @Test
    void findByWebshopOrder_IdExistsTest() {

        // given

        Long customerId = 1L;

        // when

        List<Customer> customers = underTest.findByWebshopOrder_Id(customerId);

        // then

        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers.get(0).getFirstName()).isEqualToIgnoringCase("mislav");
        assertThat(customers.get(0).getLastName()).isEqualToIgnoringCase("Srecec");
        assertThat(customers.get(0).getEmail()).isEqualToIgnoringCase("mislav.srecec@outlook.com");

    }

    @Test
    void findByWebshopOrder_IdNotExistsTest() {

        // given

        Long customerId = 5L;

        // when

        List<Customer> customers = underTest.findByWebshopOrder_Id(customerId);

        // then

        assertThat(customers.size()).isEqualTo(0);

    }

}