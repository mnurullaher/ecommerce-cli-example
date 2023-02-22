package org.nurullah.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.model.Product;
import org.nurullah.repository.ProductRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class ProductService {
    private final Logger logger = LogManager.getLogger();
    ProductRepository productRepository = new ProductRepository();

    public void createProduct(String name, Double price, List<Integer> categories){
        Product product = new Product(name, price, Date.from(Instant.now()));
        productRepository.saveProduct(product, categories);
        logger.info("The product with id number " + product.getId() + " has successfully saved");
    }

    public void deleteProduct(int productId){
        productRepository.deleteProduct(productId);
        logger.info("The product with id number " + productId + " has successfully deleted");
    }

    public List<Product> listProducts(){
        return productRepository.listProducts();
    }
}
