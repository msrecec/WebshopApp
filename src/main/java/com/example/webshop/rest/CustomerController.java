package com.example.webshop.rest;

import com.example.webshop.command.customer.CustomerSingleSaveCommand;
import com.example.webshop.command.customer.CustomerSingleUpdateCommand;
import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @PostMapping
    ResponseEntity<CustomerDTO> save(@Valid @RequestBody final CustomerSingleSaveCommand command, @RequestParam(name = "orderId", required = false) Optional<Long> orderId) {
        return customerService.save(command, orderId)
                .map(
                     customerDTO -> ResponseEntity
                             .status(HttpStatus.CREATED)
                             .body(customerDTO)
                ).orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @PutMapping
    ResponseEntity<CustomerDTO> update(@Valid @RequestBody final CustomerSingleUpdateCommand command) {
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
