package org.nurullah.repository;

import org.nurullah.model.Product;

import java.util.List;

public interface ProductRepository {
    void saveProduct(Product product);
    void deleteProduct(int productId);
    void deleteProduct(Product product);
    List<Product> listProducts();
    void updateProduct(int productId, String newName, double newPrice);
    Product findById(int givenId);
}
