package com.example.webshop.command.order;

import com.example.webshop.command.customer.CustomerSaveCommand;
import com.example.webshop.command.orderItem.OrderItemSaveCommand;
import com.example.webshop.command.orderItem.OrderItemUpdateCommand;
import com.example.webshop.model.order.Status;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderUpdateCommand {
    @NotNull(message = "Order ID must not be null")
    @Positive(message = "Order ID must be a positive number")
    private Long id;
    @Pattern(regexp = "/DRAFT|SUBMITTED/g", message = "Order status string must be either DRAFT or SUBMITTED")
    private String status;
    @Valid
    private CustomerSaveCommand customer;
    @Valid
    private List<OrderItemUpdateCommand> orderItems;
}

