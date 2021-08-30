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
//    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name", columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "last_name", columnDefinition = "TEXT")
    private String lastName;
    @Column(name = "email" , columnDefinition = "TEXT")
    private String email;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
