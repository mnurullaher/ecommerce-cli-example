package org.nurullah.repository.query;

public class OrderQuery {
    public static final String saveOrderQuery = "INSERT INTO orders(user_id, status, createdAt) VALUES(?, ?, ?)";
    public static final String saveOrderItemsQuery = """
            INSERT INTO order_items(order_id, product_id, quantity) VALUES(?, ?, ?)
            """;
}
