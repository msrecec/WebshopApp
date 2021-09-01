package com.example.webshop.command.order;

import com.example.webshop.command.customer.CustomerMultipleCommand;
import com.example.webshop.command.orderItem.multiple.OrderItemMultipleUpdateCommand;
import com.example.webshop.model.order.Status;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Order status must not be null")
    private Status status;
}

