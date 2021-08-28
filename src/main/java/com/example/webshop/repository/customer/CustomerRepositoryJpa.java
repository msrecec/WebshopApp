package com.example.webshop.repository.customer;

import com.example.webshop.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepositoryJpa extends JpaRepository<Customer, Long> {
    Customer save(Customer customer);
}
