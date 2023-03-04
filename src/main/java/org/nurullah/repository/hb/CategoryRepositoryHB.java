package org.nurullah.repository.hb;

import org.hibernate.Session;
import org.nurullah.model.Category;
import org.nurullah.repository.CategoryRepository;

import java.util.List;

public class CategoryRepositoryHB implements CategoryRepository {
    private final Session session;

    public CategoryRepositoryHB(Session session){
        this.session = session;
    }

    @Override
    public void saveCategory(Category category) {
        var txn = session.beginTransaction();
        session.persist(category);
        txn.commit();
    }

    @Override
    public void addProductsToCategory(int categoryId, List<Integer> productIds){

    }

    @Override
    public void deleteCategory(Category category) {
        var txn = session.beginTransaction();
        session.remove(category);
        txn.commit();
    }

    @Override
    public void  updateCategory(int categoryId, String newName){

    }

    @Override
    public List<Category> listCategories() {
        return session.createQuery(
                        "SELECT c FROM categories c", Category.class)
                .getResultList();
    }

    public Category findById(int id){
        var txn = session.beginTransaction();
        var category = session.find(Category.class, id);
        txn.commit();
        return category;
    }
}
