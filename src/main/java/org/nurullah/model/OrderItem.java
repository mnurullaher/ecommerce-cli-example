package org.nurullah.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private int productId;
    // private int orderId;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return Integer.toString(productId);
    }
}
