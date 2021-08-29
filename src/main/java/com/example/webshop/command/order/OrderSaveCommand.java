package com.example.webshop.command.order;

import com.example.webshop.command.customer.CustomerSaveCommand;
import com.example.webshop.command.orderItem.OrderItemSaveCommand;
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
    private CustomerSaveCommand customer;
    @Valid
    private List<OrderItemSaveCommand> orderItems;
}
