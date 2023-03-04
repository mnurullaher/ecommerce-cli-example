package org.nurullah.service;

import org.nurullah.model.Category;
import org.nurullah.repository.CategoryRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public CategoryService(CategoryRepository categoryRepository, ProductService productService){
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    public void createCategory(String name){
        Category category = new Category(name, Date.from(Instant.now()));
        categoryRepository.saveCategory(category);
    }

    public void deleteCategory(int categoryId){
        var category = categoryRepository.findById(categoryId);
        categoryRepository.deleteCategory(category);
    }

    public void updateCategory(int categoryId, String newName){
        var category = categoryRepository.findById(categoryId);
        category.setName(newName);
        categoryRepository.saveCategory(category);
    }

    public List<Category> listCategories(){
        return categoryRepository.listCategories();
    }

    public void addProductsToCategory(int categoryId, List<Integer> productIds){
        var category = categoryRepository.findById(categoryId);
        productIds.forEach(id -> {
            var product = productService.findById(id);
            category.getProducts().add(product);
        });
        categoryRepository.saveCategory(category);
    }
}
