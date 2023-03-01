package org.nurullah.repository;

import org.nurullah.model.Category;

import java.util.List;

public interface CategoryRepository {
    void saveCategory(Category category);
    void deleteCategory(int categoryId);
    List<Category> listCategories();
    void addProductsToCategory(int categoryId, List<Integer> productIds);
    public void  updateCategory(int categoryId, String newName);
}
