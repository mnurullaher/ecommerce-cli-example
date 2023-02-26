package org.nurullah.repository;

import org.nurullah.model.Category;

import java.util.List;

public interface CategoryRepository {
    void saveCategory(Category category);
    void deleteCategory(int categoryId);
    List<Category> listCategories();

}
