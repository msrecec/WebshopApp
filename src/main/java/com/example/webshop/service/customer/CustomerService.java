package com.example.webshop.service.customer;

import com.example.webshop.command.customer.CustomerSingleCommand;
import com.example.webshop.dto.customer.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> findCustomers();
    Optional<CustomerDTO> findCustomerById(Long id);
    Optional<CustomerDTO> update(CustomerSingleCommand command);
}
