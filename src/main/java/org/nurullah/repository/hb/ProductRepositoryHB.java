package org.nurullah.repository.hb;

import org.hibernate.Session;
import org.nurullah.model.Product;
import org.nurullah.repository.ProductRepository;

import java.util.List;

public record ProductRepositoryHB(Session session) implements ProductRepository {

    @Override
    public void saveProduct(Product product) {
        var txn = session.beginTransaction();
        session.persist(product);
        txn.commit();
    }

    @Override
    public void deleteProduct(Product product) {
        var txn = session.beginTransaction();
        session.remove(product);
        txn.commit();
    }

    @Override
    public void deleteProduct(int productId) {

    }

    @Override
    public List<Product> listProducts() {
        var txn = session.beginTransaction();
        var products = session.createQuery("SELECT p FROM products p", Product.class)
                .getResultList();
        txn.commit();
        return products;
    }

    @Override
    public Product findById(int id) {
        var txn = session.beginTransaction();
        var product = session.find(Product.class, id);
        txn.commit();
        return product;
    }

    @Override
    public void updateProduct(int productId, String newName, double newPrice) {

    }
}
