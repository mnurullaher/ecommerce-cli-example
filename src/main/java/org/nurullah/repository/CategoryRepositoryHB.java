package org.nurullah.repository;

import org.hibernate.Session;
import org.nurullah.model.Category;
import org.nurullah.model.Product;

import java.util.List;

public class CategoryRepositoryHB implements CategoryRepository{
    private final Session session;

    public CategoryRepositoryHB(Session session){
        this.session = session;
    }

    @Override
    public void saveCategory(Category category) {
        var txn = session.beginTransaction();
        session.persist(session.merge(category));
        txn.commit();
    }
    @Override
    public void addProductsToCategory(int categoryId, List<Integer> productIds){
        var txn = session.beginTransaction();
        Category category = session.find(Category.class, categoryId);
        for (var id : productIds){
            var product = session.find(Product.class, id);
            product.getCategories().add(category);
            category.getProducts().add(product);
        }
        txn.commit();
    }

    @Override
    public void deleteCategory(int categoryId) {
        var txn = session.beginTransaction();
        Category category = session.find(Category.class, categoryId);
        session.createQuery(
                        "SELECT p FROM products p", Product.class)
                .getResultList()
                .stream().filter(c->c.getCategories().contains(category))
                .forEach(c->c.getCategories().remove(category));
        category.getProducts().clear();
        session.remove(category);
        txn.commit();
    }

    @Override
    public List<Category> listCategories() {
        return session.createQuery(
                        "SELECT c FROM categories c", Category.class)
                .getResultList();
    }
}
