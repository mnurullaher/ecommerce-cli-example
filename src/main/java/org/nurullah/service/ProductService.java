package org.nurullah.service;

import org.nurullah.model.Product;
import org.nurullah.repository.ProductRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class ProductService {
    ProductRepository productRepository = new ProductRepository();

    public void createProduct(String name, Double price, List<Integer> categories){
        Product product = new Product(name, price, Date.from(Instant.now()));
        productRepository.saveProduct(product, categories);
    }

    public void deleteProduct(int productId){
        productRepository.deleteProduct(productId);
    }

    public List<Product> listProducts(){
        return productRepository.listProducts();
    }
}