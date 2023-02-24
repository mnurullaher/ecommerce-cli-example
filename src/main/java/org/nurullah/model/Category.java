package org.nurullah.model;

import java.util.Date;

public class Category {
    private int id;
    private final String name;
    private final Date createdAt;

    public Category(String name, Date createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return getName();
    }
}
