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
    public void deleteProduct(int productId) {
        var txn = session.beginTransaction();
        Product product = session.find(Product.class, productId);
        product.remove();
        session.remove(product);
        txn.commit();
    }

    public void updateProduct(int productId, String newName, double newPrice){
        var txn = session.beginTransaction();
        Product product = session.find(Product.class, productId);
        product.setName(newName);
        product.setPrice(newPrice);
        session.persist(product);
        txn.commit();
    }

    @Override
    public List<Product> listProducts() {
        var txn = session.beginTransaction();
        var products = session.createQuery("SELECT p FROM products p", Product.class)
                .getResultList();
        txn.commit();
        return products;
    }
}
