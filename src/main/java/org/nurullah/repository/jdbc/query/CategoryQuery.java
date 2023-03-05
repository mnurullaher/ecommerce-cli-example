package org.nurullah.repository.jdbc.query;

public class CategoryQuery {
    public static final String saveCategoryQuery = """
            INSERT INTO categories(id, name, createdAt) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE name = ?
            """;
    public static final String deleteFromProductCategories = "DELETE FROM categories_products WHERE categories_id = ?";
    public static final String deleteCategoryQuery = "DELETE FROM categories WHERE id = ?";
    public static final String listCategoriesQuery = "SELECT * FROM categories";
    public static final String listProductsOfCategory = """
            SELECT p.* FROM products p
            LEFT JOIN categories_products cp
            ON(p.id = cp.products_id)
            WHERE cp.categories_id = ?;
            """;
    public static final String addProductsToCategoryQuery = """
            REPLACE INTO categories_products(categories_id, products_id) VALUES(?, ?)
            """;
    public static final String findByIdQuery = """
            SELECT * FROM categories WHERE id = ?
            """;
}