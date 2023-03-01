package org.nurullah.repository;

import org.nurullah.model.Product;

import java.util.List;

public interface ProductRepository {
    void saveProduct(Product product, List<Integer> categories);
    void saveProduct(Product product);
    void deleteProduct(int productId);
    List<Product> listProducts();
    public void updateProduct(int productId, String newName, double newPrice);
}
