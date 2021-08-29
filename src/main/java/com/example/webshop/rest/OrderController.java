package com.example.webshop.rest;


import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.order.OrderCommand;
import com.example.webshop.model.product.ProductCommand;
import com.example.webshop.service.order.OrderService;
import com.example.webshop.service.order.OrderServiceImpl;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    OrderService orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> save(@Valid @RequestBody final OrderCommand command) {
        return orderService.save(command)
                .map(
                        productDTO -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(productDTO)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }
}
