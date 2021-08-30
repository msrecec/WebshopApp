package com.example.webshop.rest;

import com.example.webshop.command.customer.CustomerSingleCommand;
import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    List<CustomerDTO> findAll() {
        return customerService.findCustomers();
    }

    @GetMapping("/id/{id}")
    ResponseEntity<CustomerDTO> findById(@PathVariable final Long id) {
        return customerService.findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @PutMapping
    ResponseEntity<CustomerDTO> update(@Valid @RequestBody final CustomerSingleCommand command) {
        return customerService.update(command)
                .map(
                        customerDTO -> ResponseEntity
                                .status(HttpStatus.OK)
                                .body(customerDTO)
                ).orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

}
