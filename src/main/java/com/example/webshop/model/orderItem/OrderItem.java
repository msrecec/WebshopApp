package com.example.webshop.model.orderItem;

import com.example.webshop.model.order.Order;
import com.example.webshop.model.product.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_item", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    Order order;
    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;
}
