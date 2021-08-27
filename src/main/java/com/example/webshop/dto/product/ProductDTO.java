package com.example.webshop.dto.product;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductDTO {
    private String code;
    private String name;
    private BigDecimal priceHrk;
    private String description;
    private Boolean isAvailable;
}
