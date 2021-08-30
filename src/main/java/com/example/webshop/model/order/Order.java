package com.example.webshop.model.order;

import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.orderItem.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "webshop_order", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    @Id
//    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "total_price_hrk")
    private BigDecimal totalPriceHrk;
    @Column(name = "total_price_eur")
    private BigDecimal totalPriceEur;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
