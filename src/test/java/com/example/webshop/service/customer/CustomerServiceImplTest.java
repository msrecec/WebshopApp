package com.example.webshop.service.customer;

import com.example.webshop.command.customer.CustomerCommand;
import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.repository.customer.CustomerRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
class CustomerServiceImplTest {

    @MockBean(name = "customerRepositoryJpa")
    private CustomerRepositoryJpa customerRepositoryJpa;
    @Autowired
    private CustomerService customerService;

    @Test
    void findCustomersTest() {

        // given

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        when(customerRepositoryJpa.findAll()).thenReturn(customerList);

        // when

        List<CustomerDTO> customerDTOS = customerService.findCustomers();

        // then

        assertThat(customerDTOS.size()).isEqualTo(1);
        assertThat(customerDTOS.get(0).getFirstName()).isEqualToIgnoringCase("test");
        assertThat(customerDTOS.get(0).getLastName()).isEqualToIgnoringCase("test");

    }

    @Test
    void findCustomerByIdTest() {

        // given

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        when(customerRepositoryJpa.findById(1L)).thenReturn(Optional.of(customer));

        // when

        Optional<CustomerDTO> customers = customerService.findCustomerById(1L);

        // then

        assertThat(customers.isPresent()).isTrue();
        assertThat(customers.get().getFirstName()).isEqualToIgnoringCase("test");
        assertThat(customers.get().getLastName()).isEqualToIgnoringCase("test");

    }

    @Test
    void updateExistsTest() {

        // given
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        CustomerCommand command = CustomerCommand.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();


        when(customerRepositoryJpa.findById(1L)).thenReturn(Optional.of(customer));

        // when

        Optional<CustomerDTO> customerDTOOptional = customerService.update(command);

        // then

        assertThat(customerDTOOptional.isPresent()).isTrue();
        assertThat(customerDTOOptional.get().getFirstName()).isEqualToIgnoringCase("test");

    }

    @Test
    void updateNotExistsTest() {

        // given
        CustomerCommand command = CustomerCommand.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();


        when(customerRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

        // when

        Optional<CustomerDTO> customerDTOOptional = customerService.update(command);

        // then

        assertThat(customerDTOOptional.isEmpty()).isTrue();

    }
}