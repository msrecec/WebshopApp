package com.example.webshop.dto.order;

import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.order.Status;
import com.example.webshop.model.orderItem.OrderItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Status status;
    private BigDecimal totalPriceHrk;
    private BigDecimal totalPriceEur;
    private CustomerDTO customer;
    private List<OrderItemDTO> orderItems;
}
