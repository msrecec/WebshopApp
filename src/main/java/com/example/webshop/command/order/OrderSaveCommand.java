package com.example.webshop.command.order;

import com.example.webshop.command.customer.CustomerMultipleCommand;
import com.example.webshop.command.orderItem.multiple.OrderItemMultipleSaveCommand;
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
    private CustomerMultipleCommand customer;
    @Valid
    private List<OrderItemMultipleSaveCommand> orderItems;
}
