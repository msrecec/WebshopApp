package com.example.webshop.rest;


import com.example.webshop.command.orderItem.OrderItemPostCommand;
import com.example.webshop.command.orderItem.OrderItemPutCommand;
import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.service.orderItem.OrderItemService;
import com.example.webshop.service.orderItem.OrderItemServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/orderItem")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemServiceImpl orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    List<OrderItemDTO> findAll() {
        return orderItemService.findAll();
    }

    @GetMapping("/id/{id}")
    ResponseEntity<OrderItemDTO> findById(@PathVariable final Long id) {
        return orderItemService.findById(id)
                .map(
                        orderItemDTO ->
                                ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(orderItemDTO)
                ).orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .build()
                );
    }

    @PutMapping
    ResponseEntity<OrderItemDTO> update(@Valid @RequestBody final OrderItemPutCommand command) {
        return orderItemService.update(command)
                .map(
                        orderItemDTO ->
                                ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(orderItemDTO)
                ).orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @PostMapping
    ResponseEntity<OrderItemDTO> save(@Valid @RequestBody final OrderItemPostCommand command) {
        return orderItemService.save(command)
                .map(
                        orderItemDTO ->
                                ResponseEntity
                                        .status(HttpStatus.CREATED)
                                        .body(orderItemDTO)
                ).orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/id/{id}")
    void deleteById(@PathVariable final Long id) {
        orderItemService.deleteById(id);
    }

}
