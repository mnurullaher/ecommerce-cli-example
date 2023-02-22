package org.nurullah.repository.query;

public class ProductQuery {
    public static final String saveProductQuery = "INSERT INTO products(product_name, product_price, createdAt) " +
            "VALUES(?, ?, ?)";
    public static final String deleteProductQuery = "DELETE FROM products WHERE product_id = ?";
}
