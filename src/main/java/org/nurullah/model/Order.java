package org.nurullah.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity (name = "orders")
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
    Set<OrderItem> orderItems = new HashSet<>();

    public Order(){}

    public Order(int userId, Date createdAt) {
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
