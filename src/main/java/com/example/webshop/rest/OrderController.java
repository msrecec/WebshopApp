package com.example.webshop.rest;


import com.example.webshop.command.order.OrderPutCommand;
import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.command.order.OrderPostCommand;
import com.example.webshop.model.hnb.Currency;
import com.example.webshop.model.hnb.Hnb;
import com.example.webshop.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable final Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @PostMapping("/finalize/{id}")
    public ResponseEntity<OrderDTO> finalizeOrder(@PathVariable final Long id) {
        return orderService.finalizeOrder(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/hnb")
    public ResponseEntity<Hnb> getHnbByCurrency(@RequestParam(name = "currency") Currency currency) {
        return orderService.getHnb(currency)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public ResponseEntity<OrderDTO> save(@Valid @RequestBody final OrderPostCommand command) {
        return orderService.save(command)
                .map(
                        orderDTO -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(orderDTO)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @PutMapping
    public ResponseEntity<OrderDTO> update(@Valid @RequestBody final OrderPutCommand command) {
        return orderService.update(command)
                .map(
                        orderDTO -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(orderDTO)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable final Long id) {
        orderService.deleteById(id);
    }
}
