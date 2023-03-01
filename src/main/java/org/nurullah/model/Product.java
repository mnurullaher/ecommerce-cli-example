package org.nurullah.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private String name;
    private double price;
    private Date createdAt;
    @ManyToMany(mappedBy = "products")
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new HashSet<>();

    public Product() {
    }

    public Product(String name, double price, Date createdAt) {
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    public void remove(){
        detachFromAllCategories();
        detachFromAllOrders();
    }

    private void detachFromAllOrders() {
        for (var it = orders.iterator(); it.hasNext();) {
            var order = it.next();
            order.getProducts().remove(this);
            it.remove();
        }
    }

    private void detachFromAllCategories() {
        for (var it = categories.iterator(); it.hasNext();) {
            var category = it.next();
            category.getProducts().remove(this);
            it.remove();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return name;
    }

    public Set<Order> getOrders() {
        return orders;
    }
}
