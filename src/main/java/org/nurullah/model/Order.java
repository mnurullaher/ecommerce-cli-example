package org.nurullah.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Entity (name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    @Transient
    private int userId;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    private String status;
    private Date createdAt;
    @Transient
    private Map<String, Integer> items;

    public Order(){}

    public Order(int userId, String status, Date createdAt) {
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getStatus() {
        return status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && userId == order.userId && user.equals(order.user) && status.equals(order.status) && createdAt.equals(order.createdAt) && items.equals(order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, user, status, createdAt, items);
    }
}
