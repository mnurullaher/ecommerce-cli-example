package org.nurullah.repository;

import org.hibernate.Session;
import org.nurullah.model.Product;

import java.util.List;

public class ProductRepositoryHB implements ProductRepository{
    private final Session session;

    public ProductRepositoryHB(Session session){
        this.session = session;
    }
    @Override
    public void saveProduct(Product product, List<Integer> categories) {

    }

    @Override
    public void saveProduct(Product product) {
        var txn = session.beginTransaction();
        session.persist(session.merge(product));
        txn.commit();
    }

    @Override
    public void deleteProduct(int productId) {

    }

    @Override
    public List<Product> listProducts() {
        return null;
    }

}
