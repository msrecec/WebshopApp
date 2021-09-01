package com.example.webshop.command.orderItem;

import lombok.*;

import javax.validation.constraints.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderItemSaveCommand {
    @NotNull(message = "Order ID must not be null")
    @Positive(message = "Order ID must be a positive number")
    private Long orderId;
    @NotNull(message = "quantity must not be null")
    @PositiveOrZero(message = "quantity must be positive or zero")
    private Integer quantity;
    @NotNull(message = "Product code must not be null")
    @NotBlank(message = "Product code must not be blank")
    @Size(min = 10, max = 10, message = "Product code must be exactly 10 characters long")
    private String code;

}
