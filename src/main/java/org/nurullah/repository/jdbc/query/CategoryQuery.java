package org.nurullah.repository.jdbc.query;

public class CategoryQuery {
    public static final String saveCategoryQuery = "INSERT INTO categories(name, createdAt) " +
            "VALUES(?, ?)";
    public static final String deleteFromProductCategories = "DELETE FROM categories_products WHERE categories_id = ?";
    public static final String deleteCategoryQuery = "DELETE FROM categories WHERE id = ?";
    public static final String listCategoriesQuery = "SELECT * FROM categories";
    public static final String updateCategoryQuery = "UPDATE categories SET name = ? WHERE id = ?";
    public static final String addProductsToCategoryQuery = """
            INSERT INTO categories_products(categories_id, products_id) 
            VALUES(?, ?)
            """;
    public static final String findByIdQuery = """
            SELECT * FROM categories 
            WHERE id = ?
            """;
}