package com.example.webshop.command.product;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductCommand {
    @NotNull(message = "Product code must not be null")
    @NotBlank(message = "Product code must not be blank")
    @Size(min = 10, max = 10, message = "Product code must be exactly 10 characters long")
    private String code;
    @NotNull(message = "Product name must not be null")
    @NotBlank(message = "Product name must not be blank")
    @Size(min = 1, max = 100, message = "Product name length must be between 1 and 100 characters (including 1 and 100)")
    private String name;
    @NotNull(message = "Product price must not be null")
    @PositiveOrZero()
    private BigDecimal priceHrk;
    @NotNull(message = "Product description must not be null")
    @NotBlank(message = "Product description must not be blank")
    private String description;
    @NotNull(message = "Product availability must not be null")
    private Boolean isAvailable;
}
