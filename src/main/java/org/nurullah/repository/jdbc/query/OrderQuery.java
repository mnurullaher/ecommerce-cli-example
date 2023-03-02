package org.nurullah.repository.jdbc.query;

public class OrderQuery {
    public static final String saveOrderQuery = "INSERT INTO orders(userId, createdAt) VALUES(?, ?)";
    public static final String saveOrderItemsQuery = """
            INSERT INTO OrderItem(orderId, productId, quantity) VALUES(?, ?, ?)
            """;
    public static final String deleteFromOrderItems = "DELETE FROM OrderItem WHERE orderId = ?";
    public static final String deleteOrderQuery = "DELETE FROM orders WHERE id = ?";
    public static final String listOrdersQuery = "SELECT * FROM orders";
    public static final String listOrderItemsQuery = """
            SELECT p.name, oi.quantity  FROM products p
            LEFT JOIN OrderItem oi\s
            ON(p.id = oi.productId)
            WHERE oi.orderId = ?;
            """;

    public static final String addProductsToOrderQuery = """
            INSERT INTO OrderItem(orderId, productId, quantity) 
            VALUES(?, ?, ?)
            """;
}
