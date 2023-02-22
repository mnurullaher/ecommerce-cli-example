package org.nurullah.repository.query;

public class CategoryQuery {
    public static final String saveCategoryQuery = "INSERT INTO categories(category_id, category_name, createdAt) " +
            "VALUES(?, ?, ?)";
    public static final String deleteCategoryQuery = "DELETE FROM categories WHERE category_id = ?";
}
