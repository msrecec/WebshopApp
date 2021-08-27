package com.example.webshop.model.product;

import com.example.webshop.model.orderItem.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
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
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private OrderItem orderItem;
}
