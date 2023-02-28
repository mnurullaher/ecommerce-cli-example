package org.nurullah.service;

import org.nurullah.model.Category;
import org.nurullah.model.Product;
import org.nurullah.repository.CategoryRepository;
import org.nurullah.repository.ProductRepository;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(String name){
        Category category = new Category(name, Date.from(Instant.now()));
        categoryRepository.saveCategory(category);
    }

    public void addProductsToCategory(int categoryId, List<Integer> productIds){
        categoryRepository.addProductsToCategory(categoryId, productIds);
    }

    public void deleteCategory(int categoryId){
        categoryRepository.deleteCategory(categoryId);
    }

    public List<Category> listCategories(){
        return categoryRepository.listCategories();
    }
}
