package org.nurullah.service;

import org.nurullah.model.Category;
import org.nurullah.repository.CategoryRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(String name){
        Category category = new Category(name, Date.from(Instant.now()));
        categoryRepository.saveCategory(category);
    }

    public void deleteCategory(int categoryId){
        categoryRepository.deleteCategory(categoryId);
    }

    public void updateCategory(int categoryId, String newName){
        categoryRepository.updateCategory(categoryId, newName);
    }

    public List<Category> listCategories(){
        return categoryRepository.listCategories();
    }

    public void addProductsToCategory(int categoryId, List<Integer> productIds){
        categoryRepository.addProductsToCategory(categoryId, productIds);
    }
}
