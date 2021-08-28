package com.example.webshop.model.order;

import com.example.webshop.model.customer.CustomerCommand;
import com.example.webshop.model.orderItem.OrderItemCommand;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderCommand {
    @Valid
    private CustomerCommand customer;
    @Valid
    private List<OrderItemCommand> orderItems;
}
