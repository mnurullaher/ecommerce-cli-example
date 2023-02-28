package org.nurullah.service;

import org.nurullah.model.Product;
import org.nurullah.repository.CategoryRepository;
import org.nurullah.repository.ProductRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public void createProduct(String name, Double price){
        Product product = new Product(name, price, Date.from(Instant.now()));
        productRepository.saveProduct(product);
    }

    public void deleteProduct(int productId){
        productRepository.deleteProduct(productId);
    }

    public List<Product> listProducts(){
        return productRepository.listProducts();
    }
}