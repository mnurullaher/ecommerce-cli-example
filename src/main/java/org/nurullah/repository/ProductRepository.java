package org.nurullah.repository;

import org.nurullah.model.Product;

import java.util.List;

public interface ProductRepository {
    void saveProduct(Product product);
    void deleteProduct(Product product);
    List<Product> listProducts();
    Product findById(int givenId);
}
