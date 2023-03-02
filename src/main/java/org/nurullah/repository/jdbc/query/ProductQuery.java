package org.nurullah.repository.jdbc.query;

public class ProductQuery {
    public static final String saveProductQuery = "INSERT INTO products(name, price, createdAt) " +
            "VALUES(?, ?, ?)";
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

    public static final String updateProductQuery = "UPDATE products SET name = ?, price = ? " +
            "WHERE id = ?";
}