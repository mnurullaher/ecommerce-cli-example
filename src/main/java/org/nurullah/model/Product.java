package org.nurullah.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "products")
@Getter @Setter @NoArgsConstructor
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

    public Product(String name, double price, Date createdAt) {
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    public void remove(){
        detachFromAllCategories();
    }

    private void detachFromAllCategories() {
        for (var it = categories.iterator(); it.hasNext();) {
            var category = it.next();
            category.getProducts().remove(this);
            it.remove();
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
