package org.nurullah.model;

import java.util.Date;
import java.util.Map;

public class Order {
    private int id;
    private final int userId;
    private final String status;
    private Date createdAt;
    private Map<String, Integer> items;

    public Order(int userId, String status, Date createdAt) {
        this.userId = userId;
        this.status = status;
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
}
