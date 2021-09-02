package com.example.webshop.command.order;

import com.example.webshop.command.order.nested.CustomerNestedInOrderCommand;
import com.example.webshop.command.order.nested.OrderItemNestedInOrderCommand;
import lombok.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderPostCommand {
    @Valid
    private CustomerNestedInOrderCommand customer;
    @Valid
    private List<OrderItemNestedInOrderCommand> orderItems;
}
