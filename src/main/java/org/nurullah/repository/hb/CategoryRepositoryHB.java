package org.nurullah.repository.hb;

import org.hibernate.Session;
import org.nurullah.model.Category;
import org.nurullah.repository.CategoryRepository;

import java.util.List;

public record CategoryRepositoryHB(Session session) implements CategoryRepository {

    @Override
    public void saveCategory(Category category) {
        var txn = session.beginTransaction();
        session.persist(category);
        txn.commit();
    }

    @Override
    public void deleteCategory(Category category) {
        var txn = session.beginTransaction();
        session.remove(category);
        txn.commit();
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
