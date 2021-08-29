package com.example.webshop.mapping.mapper.customer;

import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.model.customer.Customer;

public interface CustomerMapper {
    CustomerDTO mapCustomerToDTO(Customer customer);
}
