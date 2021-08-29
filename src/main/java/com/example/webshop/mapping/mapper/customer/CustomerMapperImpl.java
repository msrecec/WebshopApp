package com.example.webshop.mapping.mapper.customer;

import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.model.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperImpl implements CustomerMapper{

    @Override
    public CustomerDTO mapCustomerToDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }
}
