package com.example.webshop.dto.orderItem;

import com.example.webshop.dto.product.ProductDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderItemDTO {
    private Long id;
    private ProductDTO product;
    private Integer quantity;
}
