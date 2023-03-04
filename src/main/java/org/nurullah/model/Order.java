package org.nurullah.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity (name = "orders")
@Getter @Setter @NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private Integer userId;
    private Date createdAt;
    @Transient
    private Map<String, Integer> items;
    @JoinColumn(name = "orderId")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    public Order(int userId, Date createdAt) {
        this.userId = userId;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
