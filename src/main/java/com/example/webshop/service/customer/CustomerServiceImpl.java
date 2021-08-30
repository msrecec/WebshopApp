package com.example.webshop.service.customer;

import com.example.webshop.command.customer.CustomerSingleCommand;
import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.mapping.mapper.customer.CustomerMapper;
import com.example.webshop.mapping.mapper.customer.CustomerMapperImpl;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.order.Order;
import com.example.webshop.repository.customer.CustomerRepositoryJpa;
import com.example.webshop.repository.order.OrderRepositoryJpa;
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
    private final OrderRepositoryJpa orderRepositoryJpa;
    private final Session session;

    @Autowired
    public CustomerServiceImpl(CustomerMapperImpl mapper,
                               CustomerRepositoryJpa customerRepositoryJpa,
                               OrderRepositoryJpa orderRepositoryJpa,
                               Session session) {
        this.mapper = mapper;
        this.customerRepositoryJpa = customerRepositoryJpa;
        this.orderRepositoryJpa = orderRepositoryJpa;
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

    /**
     * Saves the customer to DB , if orderId is present it also updates order table to match foreign key of customer id
     *
     * @param command
     * @param orderId
     * @return
     */

    @Override
    @Transactional
    public Optional<CustomerDTO> save(CustomerSingleCommand command, Optional<Long> orderId) {
        Customer customer;
        if(orderId.isPresent()) {
            Optional<Order> order = orderRepositoryJpa.findById(orderId.get());
            if(order.isPresent()) {
                customer = Customer.builder()
                        .firstName(command.getFirstName())
                        .lastName(command.getLastName())
                        .email(command.getEmail())
                        .build();

                customer = customerRepositoryJpa.save(customer);

                order.get().setCustomer(customer);

                session.merge(order.get());

            } else {
                return Optional.empty();
            }
        } else {
            customer = Customer.builder()
                    .firstName(command.getFirstName())
                    .lastName(command.getLastName())
                    .email(command.getEmail())
                    .build();

            customer = customerRepositoryJpa.save(customer);
        }
        return Optional.of(mapper.mapCustomerToDTO(customer));
    }

    @Override
    @Transactional
    public Optional<CustomerDTO> update(CustomerSingleCommand command) {
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

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Customer> customer = customerRepositoryJpa.findById(id);
        if(customer.isPresent()) {
            session.remove(customer.get());
        }
    }
}
