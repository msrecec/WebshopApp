package com.example.webshop.service.order;

import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.command.order.OrderSaveCommand;
import com.example.webshop.command.order.OrderUpdateCommand;
import com.example.webshop.model.hnb.Hnb;

import java.util.List;
import java.util.Optional;

public interface OrderService {
     Optional<OrderDTO> save(OrderSaveCommand command);
     Optional<OrderDTO> update(OrderUpdateCommand command);
     Optional<OrderDTO> findById(Long id);
     List<OrderDTO> findAll();
     Optional<Hnb> getHnbApi();
     Optional<OrderDTO> finalizeOrder(Long id);
     void deleteById(Long id);
}
