package com.example.webshop.service.customer;

import com.example.webshop.command.customer.CustomerCommand;
import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.mapping.mapper.customer.CustomerMapper;
import com.example.webshop.mapping.mapper.customer.CustomerMapperImpl;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.repository.customer.CustomerRepositoryJpa;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper mapper;
    private final CustomerRepositoryJpa customerRepositoryJpa;
    private final Session session;

    @Autowired
    public CustomerServiceImpl(CustomerMapper mapper,
                               CustomerRepositoryJpa customerRepositoryJpa,
                               Session session) {
        this.mapper = mapper;
        this.customerRepositoryJpa = customerRepositoryJpa;
        this.session = session;
    }

    @Override
    public List<CustomerDTO> findCustomers() {
        return customerRepositoryJpa.findAll().stream().map(mapper::mapCustomerToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> findCustomerById(Long id) {
        return customerRepositoryJpa.findById(id).map(mapper::mapCustomerToDTO);
    }


    @Override
    @Transactional
    public Optional<CustomerDTO> update(CustomerCommand command) {
        Optional<Customer> customer = customerRepositoryJpa.findById(command.getId());
        if(customer.isPresent()) {
            customer.get().setFirstName(command.getFirstName());
            customer.get().setLastName(command.getLastName());
            customer.get().setEmail(command.getEmail());
            session.merge(customer.get());
            return customer.map(mapper::mapCustomerToDTO);
        } else {
            return Optional.empty();
        }
    }
}
