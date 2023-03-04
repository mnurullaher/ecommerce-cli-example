package org.nurullah.service;

import org.nurullah.model.Product;
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
        var product = productRepository.findById(productId);
        product.remove();
        productRepository.deleteProduct(product);
    }

    public void updateProduct(int productId, String newName, double newPrice){
        var product = productRepository.findById(productId);
        product.setName(newName);
        product.setPrice(newPrice);
        productRepository.saveProduct(product);
    }

    public Product findById(int id){
        return productRepository.findById(id);
    }

    public List<Product> listProducts(){
        return productRepository.listProducts();
    }
}