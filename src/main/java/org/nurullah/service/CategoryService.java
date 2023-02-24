package org.nurullah.service;

import org.nurullah.model.Category;
import org.nurullah.repository.CategoryRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class CategoryService {
    CategoryRepository categoryRepository = new CategoryRepository();

    public void createCategory(String name){
        Category category = new Category(name, Date.from(Instant.now()));
        categoryRepository.saveCategory(category);
    }

    public void deleteCategory(int categoryId){
        categoryRepository.deleteCategory(categoryId);
    }

    public List<Category> listCategories(){
        return categoryRepository.listCategories();
    }
}
