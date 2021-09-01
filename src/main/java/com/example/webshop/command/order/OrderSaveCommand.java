package com.example.webshop.command.order;

import com.example.webshop.command.order.nested.CustomerInOrderCommand;
import com.example.webshop.command.order.nested.OrderItemInOrderCommand;
import lombok.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderSaveCommand {
    @Valid
    private CustomerInOrderCommand customer;
    @Valid
    private List<OrderItemInOrderCommand> orderItems;
}
