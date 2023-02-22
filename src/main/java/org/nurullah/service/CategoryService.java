package org.nurullah.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.model.Category;
import org.nurullah.repository.CategoryRepository;

import java.time.Instant;
import java.util.Date;

public class CategoryService {
    private final Logger logger = LogManager.getLogger();
    CategoryRepository categoryRepository = new CategoryRepository();

    public void createCategory(String name){
        Category category = new Category(name, Date.from(Instant.now()));
        categoryRepository.saveCategory(category);
        logger.info("The category with id number " + category.getId() + " has successfully saved");
    }
}
