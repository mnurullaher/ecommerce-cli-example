package org.nurullah.repository;

import org.hibernate.Session;
import org.nurullah.model.Product;

import java.util.List;

public record ProductRepositoryHB(Session session) implements ProductRepository {

    @Override
    public void saveProduct(Product product, List<Integer> categories) {

    }

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

//        var txn = session.beginTransaction();
//        Product product1 = session.find(Product.class, productId);
//        product1.re

//        doInTransaction(session -> {
//            Product product1 =
//                    getById(session, productId);
//            product1.remove();
//            session.delete(product1);
//        });
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
