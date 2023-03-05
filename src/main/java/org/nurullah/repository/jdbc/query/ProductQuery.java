package org.nurullah.repository.jdbc.query;

public class ProductQuery {
    public static final String saveProductQuery = """
            INSERT INTO products(id, name, price, createdAt) VALUES(?, ?, ?, ?)
             ON DUPLICATE KEY UPDATE name = ?, price = ?
            """;
    public static final String deleteProductQuery = "DELETE FROM products WHERE id = ?";
    public static final String deleteFromProductCategoryList = """
            DELETE FROM categories_products WHERE products_id = ?
            """;
    public static final String listProductCategories = """
            SELECT c.* FROM categories c
            LEFT JOIN categories_products cp
            ON(c.id = cp.categories_id)
            WHERE cp.products_id = ?;
            """;
    public static final String listProducts = "SELECT * FROM products";

    public static final String findById = "SELECT * FROM products WHERE id = ?";
}