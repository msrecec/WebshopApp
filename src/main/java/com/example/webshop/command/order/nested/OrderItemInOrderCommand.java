package com.example.webshop.command.order.nested;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderItemInOrderCommand {
    @NotNull(message = "quantity must not be null")
    @PositiveOrZero(message = "quantity must be positive or zero")
    private Integer quantity;
    @NotNull(message = "Product code must not be null")
    @NotBlank(message = "Product code must not be blank")
    @Size(min = 10, max = 10, message = "Product code must be exactly 10 characters long")
    private String code;

}
