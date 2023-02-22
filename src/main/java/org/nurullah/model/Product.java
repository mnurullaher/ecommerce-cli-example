package org.nurullah.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Product {
    private int id;
    private final String name;
    private final double price;
    private final Date createdAt;
    private List<Category> categories;

    public Product(String name, double price, Date createdAt) {
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        var categoriesStr = getCategories().stream().map(Category::getName).collect(Collectors.joining(","));
        return String.format("|Id: %s| Name: %s Price: %s Categories: %s CreatedAt: %s",
                getId(), getName(), getPrice(), categoriesStr, getCreatedAt());
    }
}
