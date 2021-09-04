package com.example.webshop.service.order;

import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.command.order.OrderPostCommand;
import com.example.webshop.command.order.OrderPutCommand;
import com.example.webshop.model.hnb.Currency;
import com.example.webshop.model.hnb.Hnb;

import java.util.List;
import java.util.Optional;

public interface OrderService {
     Optional<OrderDTO> save(OrderPostCommand command);
     Optional<OrderDTO> update(OrderPutCommand command);
     Optional<OrderDTO> findById(Long id);
     List<OrderDTO> findAll();
     Optional<Hnb> getHnb(Currency currency);
     Optional<OrderDTO> finalizeOrder(Long id);
     void deleteById(Long id);
}
