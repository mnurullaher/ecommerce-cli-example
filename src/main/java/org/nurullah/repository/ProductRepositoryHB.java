package org.nurullah.repository;

import org.hibernate.Session;
import org.nurullah.model.Category;
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
        var txn = session.beginTransaction();
        Product product = session.find(Product.class, productId);
        session.createQuery(
                        "SELECT c FROM categories c", Category.class)
                .getResultList()
                .stream().filter(c->c.getProducts().contains(product))
                .forEach(c->c.getProducts().remove(product));
        product.getCategories().clear();
        session.remove(product);
        txn.commit();
    }

    @Override
    public List<Product> listProducts() {
        return session.createQuery("SELECT p FROM products p", Product.class)
                .getResultList();
    }

}
