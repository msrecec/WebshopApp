package com.example.webshop.service.customer;

import com.example.webshop.command.customer.CustomerCommand;
import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.repository.customer.CustomerRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerServiceImplTest {

    @MockBean
    private CustomerRepositoryJpa customerRepositoryJpa;
    @Autowired
    private CustomerService underTest;

    Customer customer;
    CustomerCommand customerCommand;

    @BeforeEach
    void setUp() {
        this.customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        this.customerCommand = CustomerCommand.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();
    }

    @Test
    void findCustomersTest() {

        // given

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        when(customerRepositoryJpa.findAll()).thenReturn(customerList);

        // when

        List<CustomerDTO> customerDTOS = underTest.findCustomers();

        // then

        assertThat(customerDTOS.size()).isEqualTo(1);
        assertThat(customerDTOS.get(0).getFirstName()).isEqualToIgnoringCase("test");
        assertThat(customerDTOS.get(0).getLastName()).isEqualToIgnoringCase("test");

    }

    @Test
    void findCustomerByIdTest() {

        // given

        when(customerRepositoryJpa.findById(1L)).thenReturn(Optional.of(customer));

        // when

        Optional<CustomerDTO> customers = underTest.findCustomerById(1L);

        // then

        assertThat(customers.isPresent()).isTrue();
        assertThat(customers.get().getFirstName()).isEqualToIgnoringCase("test");
        assertThat(customers.get().getLastName()).isEqualToIgnoringCase("test");

    }

    @Test
    void updateExistsTest() {

        // given


        when(customerRepositoryJpa.findById(1L)).thenReturn(Optional.of(customer));

        // when

        Optional<CustomerDTO> customerDTOOptional = underTest.update(customerCommand);

        // then

        assertThat(customerDTOOptional.isPresent()).isTrue();
        assertThat(customerDTOOptional.get().getFirstName()).isEqualToIgnoringCase("test");

    }

    @Test
    void updateNotExistsTest() {

        // given

        when(customerRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

        // when

        Optional<CustomerDTO> customerDTOOptional = underTest.update(customerCommand);

        // then

        assertThat(customerDTOOptional.isEmpty()).isTrue();

    }
}