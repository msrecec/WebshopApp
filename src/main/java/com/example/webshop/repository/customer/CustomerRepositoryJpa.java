package com.example.webshop.repository.customer;

import com.example.webshop.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryJpa extends JpaRepository<Customer, Long> {
    Customer save(Customer customer);
    @Query(value = "SELECT * FROM customer INNER JOIN webshop_order ON customer.id = webshop_order.customer_id WHERE webshop_order.id = :id", nativeQuery = true)
    List<Customer> findByWebshopOrder_Id(@Param("id")Long id);
}
