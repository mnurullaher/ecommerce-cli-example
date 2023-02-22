package org.nurullah.repository.query;

public class ProductQuery {
    public static final String saveProductQuery = "INSERT INTO products(product_name, product_price, createdAt) " +
            "VALUES(?, ?, ?)";
    public static final String deleteProductQuery = "DELETE FROM products WHERE product_id = ?";
    public static final String saveProductCategoryQuery = """
            INSERT INTO product_categories(product_id, category_id) VALUES(?, ?)
            """;
    public static final String listProductCategories = """
            SELECT c.* FROM categories c
            LEFT JOIN product_categories pc
            ON(c.category_id = pc.category_id)
            WHERE pc.product_id = ?;
            """;
    public static final String listProducts = "SELECT * FROM products";
}
