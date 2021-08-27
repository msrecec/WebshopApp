package com.example.webshop.model.customer;

import com.example.webshop.model.order.Order;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customer", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Customer {
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
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
