package com.example.webshop.service.order;

import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.order.OrderCommand;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;
import java.util.Optional;

public interface OrderService {
     Optional<OrderDTO> save(OrderCommand command);
     Optional<OrderDTO> findById(Long id);
     List<OrderDTO> findAll();
}
