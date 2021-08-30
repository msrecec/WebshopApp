package com.example.webshop.model.product;

import com.example.webshop.model.orderItem.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "product_code_unique", columnNames = "code")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
//    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
//    @Column(name = "code", nullable = false)
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "price_hrk")
    private BigDecimal priceHrk;
    @Column(name = "description")
    private String description;
    @Column(name = "is_available")
    private Boolean isAvailable;
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
