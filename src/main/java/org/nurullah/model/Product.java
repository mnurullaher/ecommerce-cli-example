package org.nurullah.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
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
    Set<Category> categories = new HashSet<>();

    public void removeCategory(Category category){
        categories.remove(category);
        category.getProducts().remove(this);
    }

    public void remove(){
        for (Category category : new ArrayList<>(categories)){
            removeCategory(category);
        }
    }

    public Product() {
    }

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
}
