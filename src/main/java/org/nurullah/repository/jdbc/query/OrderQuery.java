package org.nurullah.repository.jdbc.query;

public class OrderQuery {
    public static final String saveOrderQuery = "INSERT INTO orders(user_id, createdAt) VALUES(?, ?)";
    public static final String saveOrderItemsQuery = """
            INSERT INTO order_items(order_id, product_id, quantity) VALUES(?, ?, ?)
            """;
    public static final String deleteFromOrderItems = "DELETE FROM order_items WHERE order_id = ?";
    public static final String deleteOrderQuery = "DELETE FROM orders WHERE order_id = ?";
    public static final String listOrdersQuery = "SELECT * FROM orders";
    public static final String listOrderItemsQuery = """
            SELECT p.product_name, oi.quantity  FROM products p
            LEFT JOIN order_items oi\s
            ON(p.product_id = oi.product_id)
            WHERE oi.order_id = ?;
            """;
}
